import java.io.File

class Day4 : Day() {

    override fun task1() = passportStrings.count { it.contains(Regex(requiredRegex)) }.toLong()

    override fun task2() = passportStrings.count(::isPassportStringValid).toLong()

    private val passportStrings = inputString.split("\n\n").map { it.replace("\n", " ") }

    private fun isPassportStringValid(string: String) =
        (rules.size == string.split(" ").count {
            val field = it.substringBefore(":")
            val value = it.substringAfter(":")
            rules[field]?.invoke(value) ?: false
        })

    private val rules = mapOf<String, (String) -> Boolean>(
        "byr" to { s -> s.toInt() in 1920..2002 },
        "iyr" to { s -> s.toInt() in 2010..2020 },
        "eyr" to { s -> s.toInt() in 2020..2030 },
        "hgt" to { s ->
            when (s.substring((s.length - 2) until s.length)) {
                "cm" -> s.dropLast(2).toInt() in 150..193
                "in" -> s.dropLast(2).toInt() in 59..76
                else -> false
            }
        },
        "hcl" to { s -> s.contains(Regex("^#[0-9a-f]{6}\$")) },
        "ecl" to { s -> s.contains(Regex("^(amb|blu|brn|gry|grn|hzl|oth)\$")) },
        "pid" to { s -> s.contains(Regex("^[0-9]{9}\$")) }
    )

    private val requiredRegex = "^" + rules.keys.map { "(?=.*\\b${it}:)" }.joinToString("") + ".*\$"
}
