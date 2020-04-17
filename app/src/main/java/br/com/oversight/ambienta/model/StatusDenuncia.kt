package br.com.oversight.ambienta.model

import androidx.annotation.ColorRes
import br.com.oversight.ambienta.R

enum class StatusDenuncia(val nome: String, val color:String) {
    AGUARDANDO_ANALISE("Aguardando análise", "#103FD8"),
    RECEBIDA("Recebida pelo órgão", "#10C128");
}