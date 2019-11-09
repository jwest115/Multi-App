package com.example.multiapp
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.multiapp.databinding.FragmentDieRollerBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DieRollerFragment : Fragment() {
    private lateinit var binding: FragmentDieRollerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentDieRollerBinding>(inflater,
            R.layout.fragment_die_roller,container,false)
        // Bind this fragment class to the layout
        binding.dieRoller = this
        // Set the onClickListener for the submitButton
        binding.rollButton.setOnClickListener{rollDice()}
        binding.dieRollerToTipCalculatorButton.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_dieRollerFragment_to_tipCalculatorFragment)
        }
        return binding.root
    }

    private fun rollDice() {
//        val diceImage: ImageView = findViewById(R.id.dice_image)
        val randomInt = Random().nextInt(6) + 1
        val randomInt2 = Random().nextInt(6) + 1
        val drawableResource = when(randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        val drawableResource2 = when(randomInt2) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        binding.diceImage.setImageResource(drawableResource)
        binding.diceImage2.setImageResource(drawableResource2)
    }
}
