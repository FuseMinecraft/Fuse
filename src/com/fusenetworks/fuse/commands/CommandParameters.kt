package com.fusenetworks.fuse.commands

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

//Credit to TF
@Retention(RetentionPolicy.RUNTIME)
annotation class CommandParameters(val description: String, val usage: String, val aliases: String = "")