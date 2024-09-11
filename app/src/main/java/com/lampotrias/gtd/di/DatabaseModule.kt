package com.lampotrias.gtd.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.lampotrias.gtd.data.database.GTDDatabase
import org.koin.dsl.module

val databaseModule = module {
	single<GTDDatabase> {
		Room.databaseBuilder(
			get(),
			GTDDatabase::class.java, "gtd-database"
		)
			.fallbackToDestructiveMigration(true)
			.addCallback(object : RoomDatabase.Callback() {
				override fun onOpen(connection: SQLiteConnection) {
					super.onOpen(connection)
					try {
						connection.execSQL("INSERT INTO `tag_types` (`id`, `tag_type_name`) VALUES (1, 'Time')")
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL("INSERT INTO `tag_types` (`id`, `tag_type_name`) VALUES (2, 'Priority')")
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tags` (`tag_id`, `tag_name`, `type_id`, `icon_name`) " +
									"VALUES (1, 'tag 1 type 1', 1, 'tag 1 type 1 icon')"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tags` (`tag_id`, `tag_name`, `type_id`, `icon_name`) " +
									"VALUES (2, 'tag 2 type 1', 1, 'tag 2 type 1 icon 2')"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tags` (`tag_id`, `tag_name`, `type_id`, `icon_name`) " +
									"VALUES (3, 'tag 3 type 2', 2, 'tag 3 type 2 icon 1')"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tags` (`tag_id`, `tag_name`, `type_id`, `icon_name`) " +
									"VALUES (4, 'tag 4 type 2', 2, 'tag 4 type 2 icon 2')"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `projects` (`id`, `name`, `icon_name`) " +
									"VALUES (1, 'project 1', 'project icon 1')"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `projects` (`id`, `name`, `icon_name`) " +
									"VALUES (2, 'project 2', 'project icon 2')"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `projects` (`id`, `name`, `icon_name`) " +
									"VALUES (3, 'project 3', 'project icon 3')"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks` (`task_id`, `name`, `project_id`) " +
									"VALUES (1, 'task 1 project 1', 1)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks` (`task_id`, `name`, `project_id`) " +
									"VALUES (2, 'task 2 project 1', 1)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks` (`task_id`, `name`, `project_id`) " +
									"VALUES (3, 'task 3 no project', null)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks` (`task_id`, `name`, `project_id`) " +
									"VALUES (4, 'task 4 project 2', 2)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks` (`task_id`, `name`, `project_id`) " +
									"VALUES (5, 'task 5 project 3', 3)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}
				}
			})
			.build()
	}

	single { get<GTDDatabase>().taskDao() }
	single { get<GTDDatabase>().tagDao() }
	single { get<GTDDatabase>().tagTypeDao() }
	single { get<GTDDatabase>().projectDao() }
}