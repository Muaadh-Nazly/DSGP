// Generated by view binder compiler. Do not edit!
package com.example.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAboutUsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button btnContact;

  @NonNull
  public final Button btnRateApp;

  @NonNull
  public final Button btnSendEmail;

  @NonNull
  public final Button btnSendMessage;

  @NonNull
  public final TextView textDescription;

  @NonNull
  public final TextView textTitle;

  private ActivityAboutUsBinding(@NonNull RelativeLayout rootView, @NonNull Button btnContact,
      @NonNull Button btnRateApp, @NonNull Button btnSendEmail, @NonNull Button btnSendMessage,
      @NonNull TextView textDescription, @NonNull TextView textTitle) {
    this.rootView = rootView;
    this.btnContact = btnContact;
    this.btnRateApp = btnRateApp;
    this.btnSendEmail = btnSendEmail;
    this.btnSendMessage = btnSendMessage;
    this.textDescription = textDescription;
    this.textTitle = textTitle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAboutUsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAboutUsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_about_us, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAboutUsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_contact;
      Button btnContact = ViewBindings.findChildViewById(rootView, id);
      if (btnContact == null) {
        break missingId;
      }

      id = R.id.btn_rate_app;
      Button btnRateApp = ViewBindings.findChildViewById(rootView, id);
      if (btnRateApp == null) {
        break missingId;
      }

      id = R.id.btn_send_email;
      Button btnSendEmail = ViewBindings.findChildViewById(rootView, id);
      if (btnSendEmail == null) {
        break missingId;
      }

      id = R.id.btn_send_message;
      Button btnSendMessage = ViewBindings.findChildViewById(rootView, id);
      if (btnSendMessage == null) {
        break missingId;
      }

      id = R.id.text_description;
      TextView textDescription = ViewBindings.findChildViewById(rootView, id);
      if (textDescription == null) {
        break missingId;
      }

      id = R.id.text_title;
      TextView textTitle = ViewBindings.findChildViewById(rootView, id);
      if (textTitle == null) {
        break missingId;
      }

      return new ActivityAboutUsBinding((RelativeLayout) rootView, btnContact, btnRateApp,
          btnSendEmail, btnSendMessage, textDescription, textTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}