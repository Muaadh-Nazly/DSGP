// Generated by view binder compiler. Do not edit!
package com.example.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final TextInputEditText registerPassword;

  @NonNull
  public final TextInputLayout registerPasswordLayout;

  @NonNull
  public final TextInputEditText registerUsername;

  @NonNull
  public final TextView signInInsteadSignUp;

  @NonNull
  public final TextView signUp;

  @NonNull
  public final Button signUpButton;

  @NonNull
  public final TextView textView5;

  private ActivityRegisterBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView imageView,
      @NonNull TextInputEditText registerPassword, @NonNull TextInputLayout registerPasswordLayout,
      @NonNull TextInputEditText registerUsername, @NonNull TextView signInInsteadSignUp,
      @NonNull TextView signUp, @NonNull Button signUpButton, @NonNull TextView textView5) {
    this.rootView = rootView;
    this.imageView = imageView;
    this.registerPassword = registerPassword;
    this.registerPasswordLayout = registerPasswordLayout;
    this.registerUsername = registerUsername;
    this.signInInsteadSignUp = signInInsteadSignUp;
    this.signUp = signUp;
    this.signUpButton = signUpButton;
    this.textView5 = textView5;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.registerPassword;
      TextInputEditText registerPassword = ViewBindings.findChildViewById(rootView, id);
      if (registerPassword == null) {
        break missingId;
      }

      id = R.id.registerPasswordLayout;
      TextInputLayout registerPasswordLayout = ViewBindings.findChildViewById(rootView, id);
      if (registerPasswordLayout == null) {
        break missingId;
      }

      id = R.id.registerUsername;
      TextInputEditText registerUsername = ViewBindings.findChildViewById(rootView, id);
      if (registerUsername == null) {
        break missingId;
      }

      id = R.id.signInInsteadSignUp;
      TextView signInInsteadSignUp = ViewBindings.findChildViewById(rootView, id);
      if (signInInsteadSignUp == null) {
        break missingId;
      }

      id = R.id.signUp;
      TextView signUp = ViewBindings.findChildViewById(rootView, id);
      if (signUp == null) {
        break missingId;
      }

      id = R.id.signUpButton;
      Button signUpButton = ViewBindings.findChildViewById(rootView, id);
      if (signUpButton == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = ViewBindings.findChildViewById(rootView, id);
      if (textView5 == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((ConstraintLayout) rootView, imageView, registerPassword,
          registerPasswordLayout, registerUsername, signInInsteadSignUp, signUp, signUpButton,
          textView5);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}