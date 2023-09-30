package com.example.mealsapp.data.repository

import com.example.mealsapp.data.local.RoomDB
import com.example.mealsapp.data.mapper.toCategory
import com.example.mealsapp.data.mapper.toCategoryEntity
import com.example.mealsapp.data.mapper.toMeal
import com.example.mealsapp.data.mapper.toMealEntity
import com.example.mealsapp.data.mapper.toRandomMeal
import com.example.mealsapp.data.remote.ApiService
import com.example.mealsapp.data.remote.dto.MealDto
import com.example.mealsapp.domain.model.Category
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.model.RandomMeal
import com.example.mealsapp.domain.repository.Repository
import com.example.mealsapp.util.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImp @Inject constructor(
    private val api: ApiService,
    private val db: RoomDB
) : Repository {
    private val dao = db.dao
    override suspend fun getRandomMeal(): Flow<Resources<RandomMeal>> {
        return withContext(Dispatchers.IO) {
            flow {
                emit(Resources.Loading(true))
                val randomMeal = try {
                    api.getRandomMeal()
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(Resources.Error(e.message.toString()))
                    null
                }
                randomMeal?.let { randomMealDto ->
                    emit(Resources.Success(randomMealDto.toRandomMeal()))
                    emit(Resources.Loading(false))
                }
            }
        }
    }

    override suspend fun getCategories(fromApi: Boolean): Resources<List<Category>> {
        return withContext(Dispatchers.IO) {
            try {
                val localList = dao.getAllCategories()
                val needToCallApi = localList.isEmpty() || fromApi
                val remoteList =
                    if (needToCallApi) {
                        try {
                            api.getCategories()
                        } catch (e: Exception) {
                            null
                        }
                    } else {
                        null
                    }
                remoteList?.let { categoryListDto ->
                    dao.deleteAllCategories()
                    dao.insertCategories(categoryListDto.categories.map {
                        it.toCategoryEntity()
                    })
                }
                Resources.Success(dao.getAllCategories().map {
                    it.toCategory()
                })
            } catch (e: Exception) {
                e.printStackTrace()
                Resources.Error(e.message.toString())
            }
        }
    }

    override suspend fun getMealById(
        fromApi: Boolean,
        id: String
    ): Resources<Meal> {
        return withContext(Dispatchers.IO) {
            val localItem = dao.getMealById(id)
            val needToCallApi =
                fromApi || localItem == null || localItem?.strInstructions == "" || localItem?.strArea == ""
            val remoteList = if (needToCallApi) {
                try {
                    println("from api")
                    api.getMealById(id)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            } else {
                null
            }
            remoteList?.let { mealsDto ->
                val meal = mealsDto.meals[0]
                println("test $meal")
                if (localItem != null) {
                    dao.updateMeal(
                        localItem.idMeal,
                        meal.strInstructions,
                        meal.strArea
                    )
                    return@withContext Resources.Success(dao.getMealById(id).toMeal())
                } else {
                    dao.insertMeal(meal.toMealEntity())
                    return@withContext Resources.Success(meal.toMeal())
                }
            }
            Resources.Success(dao.getMealById(id).toMeal())
        }
    }

    override suspend fun getMealByName(fromApi: Boolean, name: String): Resources<List<Meal>> {
        return withContext(Dispatchers.IO) {
            val localList = dao.getMealsByName(name)
            val needToCallApi = fromApi || (localList.isEmpty() && name.isNotEmpty())
            val remoteList = if (needToCallApi) {
                try {
                    api.getMealByName(name)
                } catch (e: Exception) {
                    e.printStackTrace()
                    print("null")
                    null
                }
            } else {
                null
            }
            remoteList?.let {
                val mealsList: List<MealDto>? = it.meals
                val meal = mealsList?.get(0)
                println("dao $meal")
                if (meal != null && meal.idMeal != "") {
                    dao.insertMeal(meal.toMealEntity())
                    return@withContext Resources.Success(listOf(meal.toMeal()))
                }

            }
            Resources.Success(dao.getMealsByName(name).map {
                println("it ${it.toMeal()}")
                it.toMeal()
            })
        }
    }


    override suspend fun getMealsCategory(
        fromApi: Boolean,
        categoryName: String
    ): Flow<Resources<List<Meal>>> {
        return withContext(Dispatchers.IO) {
            flow {
                emit(Resources.Loading(true))
                val localList = dao.getMealsByCategory(categoryName)
                val needToCallApi = localList.isEmpty()
                if (needToCallApi.not()) {
                    println("1 ${localList.size}")
                    emit(Resources.Success(localList.map {
                        it.toMeal()
                    }))
                    emit(Resources.Loading(false))
                    return@flow
                }
                val remoteList = try {
                    api.getCategoryMeals(categoryName)
                } catch (e: Exception) {
                    emit(Resources.Error(e.message.toString()))
                    null
                }
                remoteList?.let { mealsDtoList ->
                    // dao.deleteAllMealsCategory(categoryName)
                    dao.insertAllMeals(mealsDtoList.meals.map {
                        it.toMealEntity().copy(
                            strCategory = categoryName,
                        )
                    })
                    emit(Resources.Success(dao.getMealsByCategory(categoryName).map {
                        println("2 ${it.toMeal()}")
                        it.toMeal()
                    }))
                    emit(Resources.Loading(false))
                    return@flow
                }
            }
        }
    }

    override suspend fun updateFavourite(id: String, state: Boolean) {
        withContext(Dispatchers.IO) {
            dao.updateFavourite(id, state)
        }
    }

    override suspend fun getAllFavourites(): List<Meal> {
        return withContext(Dispatchers.IO) {
            dao.getFavourites().map {
                it.toMeal()
            }
        }
    }
}