
package com.icerockdev.moko.intellij.utils

import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

fun VirtualFile.createDirectories(path: String?): VirtualFile {
    if (path == null) return this
    println("Trying to create directory ${path}")

    return runWriteAction {
        val parts = path.split('/', limit = 2)
        val firstName = parts[0]
        val lastName = parts.getOrNull(1)
        val child = this.findChild(firstName) ?: this.createChildDirectory(null, firstName)
        if (lastName != null) child.createDirectories(lastName) else child
    }
}

class PathInfo(val path: String) {
    val name: String get() = path.substringAfterLast('/', path)
    val parent: String? get() = if (path.contains('/')) path.substringBeforeLast('/', "") else null
}

fun VirtualFile.createFile(path: String, content: String, mode: FileMode = FileMode("0644")): VirtualFile {
    val file = PathInfo(path)
    val fileParent = file.parent
    val dir = this.createDirectories(fileParent)
    return runWriteAction {
        dir.createChildData(null, file.name).apply {
            setBinaryContent(content.toByteArray(Charsets.UTF_8))
            if (isInLocalFileSystem) {
                canonicalPath?.let { cp ->
                    val lfile = File(cp)
                    if (lfile.exists()) {
                        lfile.setExecutable(mode.isExecutable())
                    }
                }
            }
        }
    }
}

data class FileMode(val mode: Int) {
    constructor(octalMode: String) : this(octalMode.toInt(8))

    fun isExecutable(): Boolean {
        return ((mode ushr 6) and 1) != 0
    }
}