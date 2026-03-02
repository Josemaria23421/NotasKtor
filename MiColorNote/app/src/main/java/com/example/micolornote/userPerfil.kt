import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.micolornote.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.micolornote.Holder.UsuarioHolder
import com.example.micolornote.UserPerfilViewModel

class userPerfil : Fragment(R.layout.fragment_user_perfil) {

    private val viewModel: UserPerfilViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvDni = view.findViewById<TextView>(R.id.tvDniPerfil)
        val etFoto = view.findViewById<EditText>(R.id.etNuevaFoto)
        val etPass = view.findViewById<EditText>(R.id.etNuevaPass)
        val btnFoto = view.findViewById<Button>(R.id.btnActualizarFoto)
        val btnPass = view.findViewById<Button>(R.id.btnActualizarPass)
        val dni = UsuarioHolder.dni ?: ""
        tvDni.text = "DNI: ${dni}"

        // Botón para actualizar solo la FOTO
        btnFoto.setOnClickListener {
            val url = etFoto.text.toString()
            if (url.isNotEmpty()) {
                viewModel.updateFoto(dni, url)
                etFoto.text.clear()
            } else {
                Toast.makeText(requireContext(), "Escribe una URL", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón para actualizar solo la CONTRASEÑA
        btnPass.setOnClickListener {
            val pass = etPass.text.toString()
            if (pass.isNotEmpty()) {
                viewModel.updatePassword(dni, pass)
                etPass.text.clear()
            } else {
                Toast.makeText(requireContext(), "Escribe la nueva clave", Toast.LENGTH_SHORT).show()
            }
        }

        // Observar respuesta del servidor
        viewModel.status.observe(viewLifecycleOwner) { mensaje ->
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
        }
    }
}