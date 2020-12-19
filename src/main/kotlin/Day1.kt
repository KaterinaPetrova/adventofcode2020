class Day1 : Day() {

    override fun task1(): Long {
        val digits = inputLines.map { it.toInt() }
        for (i in digits.indices) {
            for (j in i until digits.size) {
                if (digits[i] + digits[j] == 2020)
                    return digits[i] * digits[j].toLong()
            }
        }
        return -1
    }

    override fun task2(): Long {
        val digits = inputLines.map { it.toInt() }
        for (i in digits.indices) {
            for (j in i until digits.size) {
                for (z in j until digits.size) {
                    if (digits[i] + digits[j] + digits[z] == 2020) {
                        return (digits[i] * digits[j] * digits[z]).toLong()
                    }
                }
            }
        }
        return -1
    }
}

