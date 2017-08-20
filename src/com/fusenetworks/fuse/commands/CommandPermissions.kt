package com.fusenetworks.fuse.commands

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
annotation //Credit to TF
class CommandPermissions(val source: SourceType, val blockHostConsole: Boolean = false)