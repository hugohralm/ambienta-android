package br.com.oversight.ambienta.model

enum class StatusDenuncia(val nome: String, val color:String) {
    AGUARDANDO_ANALISE("Aguardando análise", "#103FD8"),
    EM_APURACAO("Em apuração", "#0fbdba"),
    CONCLUIDA("Concluída", "#08bf29"),
    DESQUALIFICADA ("Desqualificada", "#c70606");
}