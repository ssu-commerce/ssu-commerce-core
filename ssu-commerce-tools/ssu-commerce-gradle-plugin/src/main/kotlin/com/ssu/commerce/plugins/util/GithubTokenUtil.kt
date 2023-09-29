package com.ssu.commerce.plugins.util

import org.gradle.api.Project

fun Project.findUserName() = (findProperty("gpr.user") as String?).nullWhenEmpty() ?: System.getenv("USERNAME")
fun Project.findToken() = (project.findProperty("gpr.key") as String?).nullWhenEmpty() ?: System.getenv("TOKEN")
fun String?.nullWhenEmpty() = if (this.isNullOrEmpty()) null else this