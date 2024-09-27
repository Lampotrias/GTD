package com.lampotrias.gtd.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.lampotrias.gtd.data.database.GTDDatabase
import com.lampotrias.gtd.data.database.tasks.TaskEntity
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
							"INSERT INTO `tasks` (`task_id`, `name`, `project_id`, `description`, `list`, `is_completed`) " +
									"VALUES (1, 'task 1 project 1', 1, 'task 1 project 1 description', '${TaskEntity.LIST_NEXT}', 0)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks` (`task_id`, `name`, `project_id`, `description`, `list`, `is_completed`) " +
									"VALUES (2, 'task 2 project 1', 1, 'task 2 project 1 description', '${TaskEntity.LIST_WAITING}', 1)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks` (`task_id`, `name`, `project_id`, `description`, `list`, `is_completed`) " +
									"VALUES (3, 'task 3 no project', null, 'task 3 no project description', '${TaskEntity.LIST_SOMEDAY}', 0)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks` (`task_id`, `name`, `project_id`, `description`, `list`, `is_completed`) " +
									"VALUES (4, 'task 4 project 2', 2, 'task 4 project 2 description', '${TaskEntity.LIST_INBOX}', 0)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks` (`task_id`, `name`, `project_id`, `description`, `list`, `is_completed`) " +
									"VALUES (5, 'task 5 project 3', 3, 'task 5 project 3 description', '${TaskEntity.LIST_CALENDAR}', 0)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks_tags` (`task_id`, `tag_id`) " +
									"VALUES (1, 1)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks_tags` (`task_id`, `tag_id`) " +
									"VALUES (1, 2)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks_tags` (`task_id`, `tag_id`) " +
									"VALUES (1, 3)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks_tags` (`task_id`, `tag_id`) " +
									"VALUES (2, 2)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks_tags` (`task_id`, `tag_id`) " +
									"VALUES (3, 1)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks_tags` (`task_id`, `tag_id`) " +
									"VALUES (4, 2)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks_tags` (`task_id`, `tag_id`) " +
									"VALUES (4, 3)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks_tags` (`task_id`, `tag_id`) " +
									"VALUES (5, 1)"
						)
					} catch (ex: Exception) {
						ex.printStackTrace()
					}

					try {
						connection.execSQL(
							"INSERT INTO `tasks_tags` (`task_id`, `tag_id`) " +
									"VALUES (5, 3)"
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