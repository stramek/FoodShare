package com.marcinstramowski.socialmeal.screens.account.signUp

import android.os.Bundle
import com.jakewharton.rxbinding2.widget.textChanges
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.extensions.setCompatError
import com.marcinstramowski.socialmeal.model.signUp.SignUpFormFields
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import com.marcinstramowski.socialmeal.screens.main.MainActivity
import io.reactivex.Observable
import io.reactivex.functions.Function5
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * Screen that allows user sign up to the application
 */
class SignUpFragment : BaseFragment<SignUpContract.Presenter>(), SignUpContract.View {

    @Inject
    override lateinit var presenter: SignUpContract.Presenter

    override val contentViewId = R.layout.fragment_sign_up


    override fun onCreated(savedInstanceState: Bundle?) {
        observeFormFieldsChanges()
        signUpProgressButton.setOnClickListener {
            presenter.onSignUpButtonClick(
                collectSignUpFormData()
            )
        }
    }

    private fun collectSignUpFormData(): SignUpFormFields {
        return SignUpFormFields(
            signUpName.text.toString().trim(),
            signUpSurname.text.toString().trim(),
            signUpEmail.text.toString(),
            signUpPassword.text.toString(),
            signUpConfirmPassword.text.toString()
        )
    }

    override fun setSignUpButtonProcessing(processing: Boolean) {
        signUpProgressButton.setProcessing(processing)
    }

    override fun setSignUpButtonEnabled(enabled: Boolean) {
        signUpProgressButton.isEnabled = enabled
    }

    override fun showErrorMessage(stringId: Int) {
        toast(stringId)
    }

    override fun showInvalidEmailMessage(visible: Boolean) {
        signUpEmail.setCompatError(if (visible) R.string.sign_up_invalid_email else null)
    }

    override fun showMainActivity() {
        activity?.finish()
        startActivity<MainActivity>()
    }

    override fun showInvalidPasswordMessage(visible: Boolean) {
        signUpPassword.setCompatError(if (visible) R.string.sign_up_invalid_password else null)
    }

    override fun showPasswordsDontMatchMessage(visible: Boolean) {
        signUpConfirmPassword.setCompatError(if (visible) R.string.sign_up_passwords_do_not_match else null)
    }

    private fun observeFormFieldsChanges() {
        val formFieldsChanges: Observable<SignUpFormFields> = Observable.combineLatest(
            signUpName.textChanges(),
            signUpSurname.textChanges(),
            signUpEmail.textChanges(),
            signUpPassword.textChanges(),
            signUpConfirmPassword.textChanges(),
            Function5 { _, _, _, _, _ -> collectSignUpFormData() }
        )
        presenter.observeFieldsChanges(formFieldsChanges)
    }
}