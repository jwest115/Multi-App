package com.example.multiapp


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.example.multiapp.databinding.FragmentTipCalculatorBinding
import java.text.DecimalFormat



/**
 * A simple [Fragment] subclass.
 */
class TipCalculatorFragment : Fragment() {
    var tipAmount : Double? = null
    var totalAmount : Double? = null
    private lateinit var binding: FragmentTipCalculatorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentTipCalculatorBinding>(inflater,
            R.layout.fragment_tip_calculator,container,false)
        // Bind this fragment class to the layout
        binding.tipCalculator = this

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener{calculateTip()}

        setHasOptionsMenu(true)

        return binding.root
    }


    fun calculateTip() {
        var price = (binding.priceInput).text.toString().toDouble()
        var tipPercent : Double? = null
        if(binding.fifteenPercent.isChecked) {
            tipPercent = 0.15
        }
        else if(binding.eighteenPercent.isChecked) {
            tipPercent = 0.18
        }
        else {
            tipPercent = 0.20
        }
        var tip = price * tipPercent
        val df = DecimalFormat("#.##")

        tipAmount = df.format(tip).toDouble()
        totalAmount = price + tip

        binding.tipTextView.text = "Tip Amount: $tipAmount"
        binding.totalTextView.text = "Total Amount: $totalAmount"
    }

    // Creating our Share Intent
    private fun getShareIntent() : Intent {
        var tip = ""
        var total = ""
        if(tipAmount != null || totalAmount != null) {
            tip = "Tip Amount: " + tipAmount.toString()
            total = "Total Amount: " + totalAmount.toString()
        }
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, tip, total))
        return shareIntent
    }

    // Starting an Activity with our new Intent
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    // Showing the Share Menu Item Dynamically
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.email_menu, menu)
        // check if the activity resolves
        if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.share)?.setVisible(false)
        }
    }

    // Sharing from the Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}
