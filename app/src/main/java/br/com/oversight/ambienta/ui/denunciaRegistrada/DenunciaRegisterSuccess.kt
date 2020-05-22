package br.com.oversight.ambienta.ui.denunciaRegistrada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.oversight.ambienta.databinding.FragmentDenunciaRegisterSuccessBinding


class DenunciaRegisterSuccess : Fragment() {
    val args: DenunciaRegisterSuccessArgs by navArgs()

    lateinit var binding: FragmentDenunciaRegisterSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDenunciaRegisterSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.binding.codigo.text = args.codigo
        this.binding.btnOk.setOnClickListener {
            findNavController().navigate(
                DenunciaRegisterSuccessDirections.actionGlobalNavHome()
            )
        }
    }

}
