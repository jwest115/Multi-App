package com.example.multiapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.multiapp.databinding.FragmentRandomNumberBinding
import kotlinx.android.synthetic.main.fragment_random_number.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class RandomNumberFragment : Fragment() {
    var previous = -1
    var number1 = -1
    var number2 = -1
    var list = mutableListOf<Int>()
    private lateinit var binding: FragmentRandomNumberBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentRandomNumberBinding>(inflater,
            R.layout.fragment_random_number,container,false)
        // Bind this fragment class to the layout
        binding.randomNumber = this

        // Set the onClickListener for the submitButton
        binding.withoutReplacementCheckBox.setOnClickListener{clearCheckBox()}
        binding.getNumberButton.setOnClickListener{generateNumber()}
        //The complete onClickListener with Navigation
        binding.toDiceFragmentButton.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_randomNumberFragment_to_dieRollerFragment)
        }
        return binding.root
    }

    private fun clearCheckBox() {
        val numberInput1: EditText = binding.numberInput1
        val numberInput2: EditText = binding.numberInput2
        numberInput1.setText("")
        numberInput2.setText("")
        generated_number_view.text = ""
    }

    private fun generateNumber() {
        val n1 = number_input_1.text.toString().toInt()
        val n2 = number_input_2.text.toString().toInt()

        // CHECK TO SEE IF WITHOUT REPLACEMENT CHECK BOX IS CHECKED
        if(without_replacement_check_box.isChecked) {
            // IF THE NUMBERS CHANGE THEN RESET
            if(n1 != number1 || n2 != number2) {
                number1 = n1
                number2 = n2
                list.clear()
                var randomInt = Random().nextInt((number2 - number1) + 1) + number1
                list.add(randomInt)
                generated_number_view.text = randomInt.toString()
            }

            // GET RANDOM INTS THAT ARE NOT EQUAL TO THE NUMBER TO NOT REPLACE
            else {
                var randomInt = Random().nextInt((number2 - number1) + 1) + number1
                if(list.size < (number2 - number1 ) + 1) {
                    // IF THE RANDOM INT IS EQUAL TO THE NUMBER TO NOT REPLACE, LOOP UNTIL A DIFFERENT NUMBER IS FOUND
                    while (list.contains(randomInt)) {
                        randomInt = Random().nextInt((number2 - number1) + 1) + number1
                    }
                    previous = randomInt
                    list.add(randomInt)
                    generated_number_view.text = randomInt.toString()
                }
                else {
                    generated_number_view.text = previous.toString()
                }
            }
        }

        // CHECKBOX IS NOT CHECKED, ONLY FIND RANDOM INTS WITHIN RANGE
        else {
            list.clear()
            previous = -1
            number1 = -1
            number2 = -1
            val randomInt = Random().nextInt((n2 - n1) + 1) + n1
            generated_number_view.text = randomInt.toString()
        }
    }
}
