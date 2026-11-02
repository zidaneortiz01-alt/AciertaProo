package com.example.data.repository

import com.example.domain.model.CompetencyScore
import com.example.domain.model.EssayRubric
import com.example.domain.model.ExamAttempt
import com.example.domain.model.InclusiveSettings
import com.example.domain.model.ModuleCode
import com.example.domain.model.ModuleResult
import com.example.domain.model.Question
import com.example.domain.model.QuestionOption
import com.example.domain.model.QuestionType

interface ExamRepository {
    fun getQuestionsForSession(sessionNumber: Int): List<Question>
    fun getEssayPrompt(): String
}

interface AttemptRepository {
    fun getAttempts(): List<ExamAttempt>
    fun getAttemptById(id: String): ExamAttempt?
    fun saveAttempt(attempt: ExamAttempt)
    fun getPausedAttempt(): ExamAttempt?
    fun deleteAttempt(id: String)
    fun restartAttempt(id: String)
}

interface SettingsRepository {
    fun getSettings(): InclusiveSettings
    fun updateSettings(settings: InclusiveSettings)
}

object MockDataStore {

    val essayPrompt = """
        Escriba un texto argumentativo en el que exponga su postura crítica frente a la siguiente cuestión:
        
        "¿Deberían las empresas y entidades públicas en Colombia regular o limitar el uso de sistemas automatizados e inteligencia artificial en la selección y evaluación del talento humano, considerando el riesgo de sesgos algorítmicos frente a la eficiencia operativa?"
        
        Justifique su posición con argumentos consistentes y ejemplos pertinentes.
    """.trimIndent()

    val baseQuestions: List<Question> = listOf(
        // Módulo 01: Lectura Crítica (Pregunta 1 a 30)
        Question(
            globalNumber = 1,
            moduleQuestionIndex = 1,
            moduleCode = ModuleCode.LECTURA_CRITICA,
            contextText = "En su 'Tratado de la naturaleza humana', David Hume sostiene que la razón es y solo debe ser esclava de las pasiones, y no puede aspirar a ninguna otra función que la de servirlas y obedecerlas. La moralidad, por ende, suscita pasiones y produce o previene acciones mientras que la razón por sí sola es totalmente impotente en este aspecto.",
            questionText = "¿Cuál de las siguientes afirmaciones sintetiza con mayor fidelidad la tesis del autor?",
            options = listOf(
                QuestionOption("A", "La moralidad depende enteramente de la deliberación racional abstracta."),
                QuestionOption("B", "Las pasiones subordinan a la razón en la determinación del juicio moral y la acción."),
                QuestionOption("C", "La razón y las pasiones actúan como fuerzas simétricas en el comportamiento humano."),
                QuestionOption("D", "El razonamiento lógico previene las acciones contrarias al bienestar individual.")
            ),
            correctOptionId = "B",
            explanation = "Hume postula explícitamente que la razón está subordinada a las pasiones en la generación de impulsos morales y acciones prácticas.",
            competence = "Identificar y entender contenidos locales"
        ),
        Question(
            globalNumber = 2,
            moduleQuestionIndex = 2,
            moduleCode = ModuleCode.LECTURA_CRITICA,
            contextText = "El avance incesante de las redes sociales ha transformado no solo la velocidad con que circula la información, sino el criterio de verosimilitud de los ciudadanos. Hoy día, una premisa no se acepta porque haya superado un escrutinio empírico riguroso, sino porque resuena con los sesgos preconcebidos de quien la lee.",
            questionText = "El autor del texto asume implícitamente que:",
            options = listOf(
                QuestionOption("A", "El rigor empírico solía ser el criterio primordial de validación de la información."),
                QuestionOption("B", "Las redes sociales son las únicas responsables del declive en la educación ciudadana."),
                QuestionOption("C", "Los sesgos cognitivos aparecieron con las nuevas plataformas digitales."),
                QuestionOption("D", "Ningún lector contemporáneo verifica la veracidad de las fuentes periodísticas.")
            ),
            correctOptionId = "A",
            explanation = "Al contrastar el estado actual con la noción de superar un escrutinio empírico, el autor presupone que dicho rigor constituía el estándar previo.",
            competence = "Comprender la articulación de las partes de un texto"
        ),
        Question(
            globalNumber = 3,
            moduleQuestionIndex = 3,
            moduleCode = ModuleCode.LECTURA_CRITICA,
            contextText = "En 'Vigilar y Castigar', Foucault describe el tránsito del suplicio público a la disciplina carcelaria moderna no como una humanización de la pena, sino como un refinamiento de la economía del poder sobre los cuerpos.",
            questionText = "La postura de Foucault puede calificarse fundamentalmente como:",
            options = listOf(
                QuestionOption("A", "Una apología del sistema punitivo tradicional del Antiguo Régimen."),
                QuestionOption("B", "Una crítica desmitificadora del relato progresista de las reformas penitenciarias."),
                QuestionOption("C", "Una propuesta técnica para mejorar la reinserción social de los reclusos."),
                QuestionOption("D", "Una justificación de la necesidad de vigilancia biométrica permanente.")
            ),
            correctOptionId = "B",
            explanation = "Foucault deconstruye la visión humanitarista estándar demostrando que el castigo se transformó en un mecanismo disciplinario más invasivo.",
            competence = "Reflexionar a partir de un texto y evaluar su contenido"
        ),

        // Módulo 02: Razonamiento Cuantitativo (Pregunta 31 a 60)
        Question(
            globalNumber = 31,
            moduleQuestionIndex = 1,
            moduleCode = ModuleCode.RAZONAMIENTO_CUANTITATIVO,
            contextText = "Un centro de cómputo universitario dispone de 3 servidores blade idénticos que procesan 180.000 transacciones en 4 horas de operación continua a plena carga.",
            questionText = "Si para el período de matrículas se incorporan 2 servidores blade adicionales de iguales características, ¿cuántas transacciones se procesarán en una ventana de 6 horas?",
            options = listOf(
                QuestionOption("A", "300.000 transacciones"),
                QuestionOption("B", "375.000 transacciones"),
                QuestionOption("C", "450.000 transacciones"),
                QuestionOption("D", "540.000 transacciones")
            ),
            correctOptionId = "C",
            explanation = "3 servidores procesan 180.000 en 4h => 45.000 tx/h total => 15.000 tx/h por servidor. Con 5 servidores: 5 * 15.000 = 75.000 tx/h. En 6 horas: 75.000 * 6 = 450.000.",
            competence = "Interpretación y representación"
        ),
        Question(
            globalNumber = 32,
            moduleQuestionIndex = 2,
            moduleCode = ModuleCode.RAZONAMIENTO_CUANTITATIVO,
            contextText = "En una base de datos relacional con 1.200.000 registros, una consulta no indexada toma en promedio 800 ms. Al añadir un índice B-Tree, el tiempo de búsqueda se reduce en un 75%. Posteriormente, al particionar la tabla horizontalmente en 4 bloques homogéneos, el tiempo indexado se reduce un 40% adicional.",
            questionText = "¿Cuál es el tiempo final promedio de respuesta de la consulta?",
            options = listOf(
                QuestionOption("A", "60 ms"),
                QuestionOption("B", "120 ms"),
                QuestionOption("C", "150 ms"),
                QuestionOption("D", "180 ms")
            ),
            correctOptionId = "B",
            explanation = "800 ms reducido un 75% deja el 25%: 800 * 0.25 = 200 ms. Luego, 200 ms reducido un 40% adicional deja el 60%: 200 * 0.60 = 120 ms.",
            competence = "Formulación y ejecución"
        ),
        Question(
            globalNumber = 44,
            moduleQuestionIndex = 14,
            moduleCode = ModuleCode.RAZONAMIENTO_CUANTITATIVO,
            contextText = "Un algoritmo de compresión de imágenes reduce un lote de datos brutos de 2.5 GB a 400 MB. Se requiere estimar la razón de compresión y el porcentaje de espacio liberado en el almacenamiento secundario.",
            questionText = "¿Cuál es el porcentaje exacto de reducción del volumen de almacenamiento?",
            options = listOf(
                QuestionOption("A", "84 %"),
                QuestionOption("B", "80 %"),
                QuestionOption("C", "76 %"),
                QuestionOption("D", "87.5 %")
            ),
            correctOptionId = "A",
            explanation = "Reducción = (2.500 MB - 400 MB) / 2.500 MB = 2.100 / 2.500 = 0.84 = 84%.",
            competence = "Argumentación"
        ),

        // Módulo 03: Competencias Ciudadanas (Pregunta 61 a 90)
        Question(
            globalNumber = 61,
            moduleQuestionIndex = 1,
            moduleCode = ModuleCode.COMPETENCIAS_CIUDADANAS,
            contextText = "En un municipio con alto índice de desempleo juvenil, el Concejo Municipal aprueba un acuerdo que exonera de impuesto predial por cinco años a empresas tecnológicas que contraten al menos un 40% de egresados locales del SENA. Un grupo de comerciantes tradicionales demanda el acuerdo aduciendo vulneración del derecho a la igualdad tributaria.",
            questionText = "En este conflicto, ¿qué principios constitucionales se encuentran en tensión directa?",
            options = listOf(
                QuestionOption("A", "La soberanía territorial frente al libre desarrollo de la personalidad."),
                QuestionOption("B", "La igualdad formal tributaria frente a la promoción de acciones afirmativas para la inclusión laboral."),
                QuestionOption("C", "El principio de legalidad penal frente a la presunción de inocencia."),
                QuestionOption("D", "La reserva de ley estatutaria frente al derecho a la huelga laboral.")
            ),
            correctOptionId = "B",
            explanation = "Colisionan la igualdad ante las cargas públicas (igualdad formal) y la potestad del Estado de otorgar incentivos diferenciales para corregir desigualdades históricas (acción afirmativa).",
            competence = "Conocimientos constitucionales y deberes"
        ),
        Question(
            globalNumber = 62,
            moduleQuestionIndex = 2,
            moduleCode = ModuleCode.COMPETENCIAS_CIUDADANAS,
            contextText = "Ante el aumento de incidentes de acoso cibernético entre estudiantes universitarios, las directivas deciden monitorear el tráfico de mensajería en las redes Wi-Fi del campus para identificar remitentes.",
            questionText = "¿Cuál es el principal reparo constitucional frente a esta medida institucional?",
            options = listOf(
                QuestionOption("A", "Que no se consultó previamente a las empresas proveedoras de telecomunicaciones."),
                QuestionOption("B", "Que viola el derecho a la intimidad y la inviolabilidad de las comunicaciones privadas sin orden judicial previa."),
                QuestionOption("C", "Que disminuye el ancho de banda disponible para fines estrictamente académicos."),
                QuestionOption("D", "Que traslada los costos del monitoreo a las matrículas de los estudiantes.")
            ),
            correctOptionId = "B",
            explanation = "El artículo 15 de la Constitución colombiana consagra la inviolabilidad de la correspondencia y demás formas de comunicación privada, salvo orden judicial.",
            competence = "Multiperspectivismo"
        ),

        // Módulo 04: Comunicación Escrita (1 tarea, global 91)
        Question(
            globalNumber = 91,
            moduleQuestionIndex = 1,
            moduleCode = ModuleCode.COMUNICACION_ESCRITA,
            contextText = essayPrompt,
            questionText = "Redacte su texto argumentativo atendiendo a los criterios de planteamiento, organización y dominio de las convenciones del lenguaje.",
            options = emptyList(),
            correctOptionId = "",
            explanation = "Evaluado mediante rúbrica holística de 3 dimensiones (18 puntos totales).",
            competence = "Comunicación Escrita Argumentativa",
            type = QuestionType.WRITTEN_TASK
        ),

        // Módulo 05: Inglés (Pregunta 92 a 135)
        Question(
            globalNumber = 92,
            moduleQuestionIndex = 1,
            moduleCode = ModuleCode.INGLES,
            contextText = "Read the notice: 'SYSTEM MAINTENANCE: Cloud storage services will be temporarily unavailable tonight from 23:00 to 03:00 UTC due to kernel upgrades. Please save all work locally.'",
            questionText = "Where would you typically see this notification?",
            options = listOf(
                QuestionOption("A", "In a department store clothing aisle"),
                QuestionOption("B", "On an IT dashboard or intranet portal"),
                QuestionOption("C", "In a highway traffic toll booth"),
                QuestionOption("D", "Inside a city subway train station")
            ),
            correctOptionId = "B",
            explanation = "The announcement describes cloud services maintenance, which belongs on an IT/computing platform.",
            competence = "Parte 1: Avisos cotidianos y contextos"
        ),
        Question(
            globalNumber = 93,
            moduleQuestionIndex = 2,
            moduleCode = ModuleCode.INGLES,
            contextText = "Software engineers frequently encounter algorithmic challenges where time complexity _____ optimal system throughput.",
            questionText = "Choose the option that correctly completes the sentence:",
            options = listOf(
                QuestionOption("A", "determines"),
                QuestionOption("B", "determining"),
                QuestionOption("C", "have determined"),
                QuestionOption("D", "determine")
            ),
            correctOptionId = "A",
            explanation = "The singular noun phrase 'time complexity' requires the third-person singular verb 'determines'.",
            competence = "Parte 4: Gramática y vocabulario en contexto"
        ),
        Question(
            globalNumber = 135,
            moduleQuestionIndex = 45,
            moduleCode = ModuleCode.INGLES,
            contextText = "Despite initial skepticism regarding asynchronous team communication, distributed engineering organizations report higher developer satisfaction when synchronous meetings are minimized in favor of documented pull request reviews.",
            questionText = "According to the passage, developer satisfaction increased primarily because:",
            options = listOf(
                QuestionOption("A", "Engineering deadlines were indefinitely postponed."),
                QuestionOption("B", "Documented asynchronous reviews replaced unnecessary synchronous meetings."),
                QuestionOption("C", "Teams returned to physical corporate offices."),
                QuestionOption("D", "Code audits were entirely delegated to artificial intelligence.")
            ),
            correctOptionId = "B",
            explanation = "The text specifies that satisfaction grew when synchronous meetings were minimized in favor of documented PR reviews.",
            competence = "Parte 7: Comprensión de lectura inferencial"
        )
    )

    val questions: List<Question> = buildAllQuestions(baseQuestions)

    val completedAttempt = ExamAttempt(
        id = "SIM-2026-2-88F4",
        seed = "seed_84920",
        sessionTitle = "Sesión 1 - Estructura 2026-2",
        isPaused = false,
        remainingSeconds = 0,
        totalDurationSeconds = 16800, // 280 min
        dateFormatted = "28 Oct 2026, 14:30",
        totalQuestions = 135,
        correctAnswers = 112,
        timeSpentFormatted = "3 h 55 min",
        averageMinutesPerQuestion = 1.7f,
        moduleResults = listOf(
            ModuleResult(
                moduleCode = ModuleCode.LECTURA_CRITICA,
                correctCount = 25,
                totalCount = 30,
                competencies = listOf(
                    CompetencyScore("Identificar contenidos locales", 9, 10),
                    CompetencyScore("Articulación del texto", 8, 10),
                    CompetencyScore("Reflexión y evaluación", 8, 10)
                )
            ),
            ModuleResult(
                moduleCode = ModuleCode.RAZONAMIENTO_CUANTITATIVO,
                correctCount = 26,
                totalCount = 30,
                competencies = listOf(
                    CompetencyScore("Interpretación y representación", 10, 10),
                    CompetencyScore("Formulación y ejecución", 9, 11),
                    CompetencyScore("Argumentación y validación", 7, 9)
                )
            ),
            ModuleResult(
                moduleCode = ModuleCode.COMPETENCIAS_CIUDADANAS,
                correctCount = 22,
                totalCount = 30,
                competencies = listOf(
                    CompetencyScore("Conocimientos constitucionales", 8, 10),
                    CompetencyScore("Multiperspectivismo", 7, 10),
                    CompetencyScore("Pensamiento social y sistémico", 7, 10)
                )
            ),
            ModuleResult(
                moduleCode = ModuleCode.INGLES,
                correctCount = 39,
                totalCount = 45,
                competencies = listOf(
                    CompetencyScore("Vocabulario y avisos (Partes 1-3)", 15, 16),
                    CompetencyScore("Estructura gramatical (Partes 4-5)", 13, 15),
                    CompetencyScore("Lectura crítica inferencial (Partes 6-7)", 11, 14)
                )
            )
        ),
        essayResponse = "El despliegue de modelos algorítmicos e inteligencia artificial en la selección laboral plantea una disyuntiva crucial para Colombia. Por un lado, optimiza los tiempos de reclutamiento en convocatorias masivas. Sin embargo, sin auditorías rigurosas e intervención humana sustantiva, estos modelos reproducen y amplifican sesgos de género, edad y procedencia socioeconómica arraigados en los conjuntos de datos históricos. Por tanto, es indispensable que el marco regulatorio nacional exija trazabilidad explicable y derechos de contradicción para los postulantes.",
        essayRubric = EssayRubric(
            planteamientoScore = 5,
            planteamientoMax = 6,
            organizacionScore = 4,
            organizacionMax = 6,
            lenguajeScore = 5,
            lenguajeMax = 6,
            feedback = "Tesis solvente con excelente contextualización local. Argumentación estructurada de forma coherente. Vocabulario técnico preciso. Nivel 3 Orientativo."
        ),
        userAnswers = mapOf(1 to "B", 2 to "A", 3 to "B", 31 to "C", 32 to "B", 44 to "A", 61 to "B", 62 to "B", 92 to "B", 93 to "A", 135 to "B"),
        flaggedQuestions = setOf(2, 62)
    )

    val pausedAttempt = ExamAttempt(
        id = "SIM-2026-S2-44C1",
        seed = "seed_33109",
        sessionTitle = "Sesión 1 - Práctica estándar",
        isPaused = true,
        pausedModuleCode = ModuleCode.RAZONAMIENTO_CUANTITATIVO,
        pausedQuestionIndex = 14,
        remainingSeconds = 9840, // 2 h 44 min restantes
        totalDurationSeconds = 16800,
        dateFormatted = "Hoy, en pausa",
        totalQuestions = 135,
        correctAnswers = 0,
        timeSpentFormatted = "1 h 16 min acumulados",
        averageMinutesPerQuestion = 1.7f,
        userAnswers = mapOf(1 to "B", 2 to "A", 31 to "C", 44 to "A"),
        flaggedQuestions = setOf(14)
    )

    val historyAttempts: MutableList<ExamAttempt> = mutableListOf(
        completedAttempt,
        pausedAttempt,
        ExamAttempt(
            id = "SIM-2026-1-71B2",
            seed = "seed_19284",
            sessionTitle = "Sesión 1 - Diagnóstico inicial",
            isPaused = false,
            remainingSeconds = 0,
            totalDurationSeconds = 16800,
            dateFormatted = "14 Oct 2026, 09:15",
            totalQuestions = 135,
            correctAnswers = 104,
            timeSpentFormatted = "4 h 10 min",
            averageMinutesPerQuestion = 1.8f
        ),
        ExamAttempt(
            id = "SIM-2026-1-55E9",
            seed = "seed_64721",
            sessionTitle = "Módulos Generales - Ensayo A",
            isPaused = false,
            remainingSeconds = 0,
            totalDurationSeconds = 16800,
            dateFormatted = "02 Oct 2026, 16:00",
            totalQuestions = 135,
            correctAnswers = 98,
            timeSpentFormatted = "3 h 48 min",
            averageMinutesPerQuestion = 1.6f
        ),
        ExamAttempt(
            id = "SIM-2026-0-12A8",
            seed = "seed_04812",
            sessionTitle = "Línea base - Prueba de entrada",
            isPaused = false,
            remainingSeconds = 0,
            totalDurationSeconds = 16800,
            dateFormatted = "18 Sep 2026, 11:20",
            totalQuestions = 135,
            correctAnswers = 89,
            timeSpentFormatted = "4 h 05 min",
            averageMinutesPerQuestion = 1.8f
        )
    )

    var currentSettings = InclusiveSettings()
}

class InMemoryExamRepository : ExamRepository {
    override fun getQuestionsForSession(sessionNumber: Int): List<Question> = MockDataStore.questions
    override fun getEssayPrompt(): String = MockDataStore.essayPrompt
}

class InMemoryAttemptRepository : AttemptRepository {
    override fun getAttempts(): List<ExamAttempt> = MockDataStore.historyAttempts
    override fun getAttemptById(id: String): ExamAttempt? = MockDataStore.historyAttempts.find { it.id == id }
    override fun saveAttempt(attempt: ExamAttempt) {
        val index = MockDataStore.historyAttempts.indexOfFirst { it.id == attempt.id }
        if (index >= 0) {
            MockDataStore.historyAttempts[index] = attempt
        } else {
            MockDataStore.historyAttempts.add(0, attempt)
        }
    }
    override fun getPausedAttempt(): ExamAttempt? = MockDataStore.historyAttempts.find { it.isPaused }
    override fun deleteAttempt(id: String) {
        MockDataStore.historyAttempts.removeAll { it.id == id }
    }
    override fun restartAttempt(id: String) {
        val attempt = MockDataStore.historyAttempts.find { it.id == id } ?: return
        val restarted = attempt.copy(
            isPaused = true,
            userAnswers = emptyMap(),
            flaggedQuestions = emptySet(),
            remainingSeconds = attempt.totalDurationSeconds,
            pausedQuestionIndex = 0,
            pausedModuleCode = ModuleCode.LECTURA_CRITICA
        )
        saveAttempt(restarted)
    }
}

class InMemorySettingsRepository : SettingsRepository {
    override fun getSettings(): InclusiveSettings = MockDataStore.currentSettings
    override fun updateSettings(settings: InclusiveSettings) {
        MockDataStore.currentSettings = settings
    }
}

private fun buildAllQuestions(baseList: List<Question>): List<Question> {
    val map = baseList.associateBy { it.globalNumber }
    return (1..135).map { num ->
        map[num] ?: when {
            num <= 30 -> Question(
                globalNumber = num,
                moduleQuestionIndex = num,
                moduleCode = ModuleCode.LECTURA_CRITICA,
                contextText = "Fragmento analítico sobre epistemología y métodos científicos contemporáneos (Texto de lectura crítica #$num). 'La replicabilidad de los experimentos delimita el horizonte de refutación de las hipótesis en pugna'.",
                questionText = "¿Cuál de las siguientes premisas es consistente con el argumento central del fragmento?",
                options = listOf(
                    QuestionOption("A", "La certeza empírica absoluta es alcanzable mediante repetición indefinida."),
                    QuestionOption("B", "La refutabilidad es el criterio demarcador que valida el método expuesto."),
                    QuestionOption("C", "Los experimentos científicos carecen de utilidad epistemológica."),
                    QuestionOption("D", "Toda hipótesis rechazada debe considerarse un fracaso metodológico.")
                ),
                correctOptionId = "B",
                explanation = "El texto enfatiza la noción de delimitación del horizonte de refutación.",
                competence = "Reflexionar a partir de un texto y evaluar su contenido"
            )
            num <= 60 -> {
                val modIdx = num - 30
                Question(
                    globalNumber = num,
                    moduleQuestionIndex = modIdx,
                    moduleCode = ModuleCode.RAZONAMIENTO_CUANTITATIVO,
                    contextText = "Un sistema distribuido procesa $modIdx lotes de información con una tasa de compresión del 20%. La capacidad disponible es de ${modIdx * 50} GB con una latencia media de ${10 + (modIdx % 5)} ms.",
                    questionText = "Si se incrementa la carga de entrada en un 25%, ¿cuál es el volumen procesado resultante?",
                    options = listOf(
                        QuestionOption("A", "${modIdx * 40} GB"),
                        QuestionOption("B", "${(modIdx * 62.5).toInt()} GB"),
                        QuestionOption("C", "${modIdx * 30} GB"),
                        QuestionOption("D", "${modIdx * 75} GB")
                    ),
                    correctOptionId = "B",
                    explanation = "Cálculo proporcional directo aplicando el factor 1.25 a la base de cálculo.",
                    competence = "Formulación y ejecución"
                )
            }
            num <= 90 -> {
                val modIdx = num - 60
                Question(
                    globalNumber = num,
                    moduleQuestionIndex = modIdx,
                    moduleCode = ModuleCode.COMPETENCIAS_CIUDADANAS,
                    contextText = "En un debate sobre políticas públicas locales (#$modIdx), una comunidad vecinal propone regular el uso del espacio público frente a la actividad de pequeños comerciantes informales.",
                    questionText = "¿Qué mecanismo constitucional garantiza la concertación pacífica entre ambas partes?",
                    options = listOf(
                        QuestionOption("A", "Una acción popular o audiencia pública de concertación ciudadana."),
                        QuestionOption("B", "El desacato administrativo de las resoluciones de movilidad."),
                        QuestionOption("C", "La suspensión unilateral de las garantías laborales de los comerciantes."),
                        QuestionOption("D", "La clausura definitiva de los corredores comerciales adyacentes.")
                    ),
                    correctOptionId = "A",
                    explanation = "La Constitución colombiana establece mecanismos participativos como audiencias y acciones ciudadanas para concertar derechos colectivos.",
                    competence = "Multiperspectivismo y argumentación ciudadana"
                )
            }
            num == 91 -> Question(
                globalNumber = 91,
                moduleQuestionIndex = 1,
                moduleCode = ModuleCode.COMUNICACION_ESCRITA,
                contextText = MockDataStore.essayPrompt,
                questionText = "Redacte su texto argumentativo atendiendo a los criterios de planteamiento, organización y dominio de las convenciones del lenguaje.",
                options = emptyList(),
                correctOptionId = "",
                explanation = "Evaluado mediante rúbrica holística de 3 dimensiones (18 puntos totales).",
                competence = "Comunicación Escrita Argumentativa",
                type = QuestionType.WRITTEN_TASK
            )
            else -> {
                val modIdx = num - 91
                Question(
                    globalNumber = num,
                    moduleQuestionIndex = modIdx,
                    moduleCode = ModuleCode.INGLES,
                    contextText = "Technical note #$modIdx: 'Automated CI/CD pipelines ensure code quality before deploying containers to cloud environments.'",
                    questionText = "What is the primary purpose of the automated pipeline described?",
                    options = listOf(
                        QuestionOption("A", "To verify software quality before release."),
                        QuestionOption("B", "To discard container registries randomly."),
                        QuestionOption("C", "To manually compile legacy desktop applications."),
                        QuestionOption("D", "To restrict developer access to cloud networks.")
                    ),
                    correctOptionId = "A",
                    explanation = "The passage explicitly highlights code quality assurance prior to deployment.",
                    competence = "Comprensión de lectura técnica en contexto de ingeniería"
                )
            }
        }
    }
}
