package com.thinkmobiles.easyerp.presentation.screens.hr.employees.details;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.thinkmobiles.easyerp.R;
import com.thinkmobiles.easyerp.domain.hr.EmployeesRepository;
import com.thinkmobiles.easyerp.presentation.adapters.crm.AttachmentAdapter;
import com.thinkmobiles.easyerp.presentation.adapters.hr.RowEmploymentDetailsAdapter;
import com.thinkmobiles.easyerp.presentation.adapters.hr.RowEmploymentJobPositionAdapter;
import com.thinkmobiles.easyerp.presentation.adapters.hr.SimpleNotesAdapter;
import com.thinkmobiles.easyerp.presentation.base.rules.content.ContentFragment;
import com.thinkmobiles.easyerp.presentation.base.rules.content.ContentPresenter;
import com.thinkmobiles.easyerp.presentation.custom.transformations.CropCircleTransformation;
import com.thinkmobiles.easyerp.presentation.holders.data.crm.AttachmentDH;
import com.thinkmobiles.easyerp.presentation.holders.data.hr.SimpleNoteDH;
import com.thinkmobiles.easyerp.presentation.managers.GoogleAnalyticHelper;
import com.thinkmobiles.easyerp.presentation.managers.HistoryAnimationHelper;
import com.thinkmobiles.easyerp.presentation.managers.ImageHelper;
import com.thinkmobiles.easyerp.presentation.utils.Constants;
import com.thinkmobiles.easyerp.presentation.utils.IntentActionHelper;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lynx on 3/17/2017.
 */

@EFragment
public class EmployeeDetailsFragment extends ContentFragment implements EmployeeDetailsContract.EmployeeDetailsView {

    private EmployeeDetailsContract.EmployeeDetailsPresenter presenter;

    @FragmentArg
    protected String employeeID;

    @Bean
    protected EmployeesRepository employeesRepository;
    @Bean
    protected RowEmploymentDetailsAdapter rowEmploymentDetailsAdapter;
    @Bean
    protected RowEmploymentJobPositionAdapter rowEmploymentJobPositionAdapter;
    @Bean
    protected AttachmentAdapter attachmentAdapter;
    @Bean
    protected SimpleNotesAdapter simpleNotesAdapter;
    @Bean
    protected HistoryAnimationHelper animationHelper;

    //region View Inject
    @ViewById
    protected NestedScrollView nsvContent_FED;
    @ViewById
    protected ImageView ivEmployeeAvatar_FED;
    @ViewById
    protected TextView tvEmployeeName_FED;
    @ViewById
    protected TextView tvHeaderDepartment_FED;
    @ViewById
    protected TextView tvHeaderJobPosition_FED;
    @ViewById
    protected ImageView ivFacebook_FED;
    @ViewById
    protected ImageView ivLinkedIn_FED;
    @ViewById
    protected ImageView ivSkype_FED;
    @ViewById
    protected ImageView ivEmail_FED;
    @ViewById
    protected EditText etFirstName_FED;
    @ViewById
    protected EditText etLastName_FED;
    @ViewById
    protected EditText etDob_FED;
    @ViewById
    protected EditText etPersonalMobile_FED;
    @ViewById
    protected EditText etSource_FED;
    @ViewById
    protected EditText etSkype_FED;
    @ViewById
    protected EditText etLinkedIn_FED;
    @ViewById
    protected EditText etFacebook_FED;
    @ViewById
    protected EditText etJobPosition_FED;
    @ViewById
    protected EditText etDepartment_FED;
    @ViewById
    protected EditText etManager_FED;
    @ViewById
    protected EditText etJobType_FED;
    @ViewById
    protected EditText etPersonalEmail_FED;
    @ViewById
    protected EditText etWorkEmail_FED;
    @ViewById
    protected EditText etWorkPhone_FED;
    @ViewById
    protected EditText etGender_FED;
    @ViewById
    protected EditText etEmploymentType_FED;
    @ViewById
    protected EditText etMaritalStatus_FED;
    @ViewById
    protected EditText etNationality_FED;
    @ViewById
    protected EditText eIdentificationNumber_FED;
    @ViewById
    protected EditText etPassportNumber_FED;
    @ViewById
    protected EditText etBankAccountNumber_FED;
    @ViewById
    protected EditText etOrderID_FED;
    @ViewById
    protected EditText etStreet_FPD;
    @ViewById
    protected EditText etCity_FPD;
    @ViewById
    protected EditText etState_FPD;
    @ViewById
    protected EditText etZipcode_FPD;
    @ViewById
    protected EditText etCountry_FPD;
    @ViewById
    protected FrameLayout btnHistory;
    @ViewById
    protected ImageView ivIconArrow;

    @ViewById
    protected RecyclerView rvJobPositionList_FED;
    @ViewById
    protected RecyclerView rvEmploymentDetailsList_FED;
    @ViewById
    protected RecyclerView rvAttachments_FED;
    @ViewById
    protected RecyclerView rvHistory;

    @ViewById
    protected LinearLayout llContainerPersonalInformation_FED;
    @ViewById
    protected LinearLayout llAddressContainer_FED;
    @ViewById
    protected LinearLayout llJobPositionListContainer_FED;
    @ViewById
    protected LinearLayout llEmploymentDetailsContainer_FED;

    @ViewById
    protected TextView tvEmptyPersonalInformation_FED;
    @ViewById
    protected TextView tvEmptyAddress_FED;
    @ViewById
    protected TextView tvEmptyJobPositionList_FED;
    @ViewById
    protected TextView tvEmptyEmploymentDetailsList_FED;
    @ViewById
    protected TextView tvEmptyAttachments_FED;


    //endregion

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_employee_details;
    }

    @AfterInject
    @Override
    public void initPresenter() {

    }

    @AfterViews
    protected void initUI() {
        GoogleAnalyticHelper.trackScreenView(this, getResources().getConfiguration());

        rvJobPositionList_FED.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvEmploymentDetailsList_FED.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAttachments_FED.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvJobPositionList_FED.setAdapter(rowEmploymentJobPositionAdapter);
        rvEmploymentDetailsList_FED.setAdapter(rowEmploymentDetailsAdapter);
        rvAttachments_FED.setAdapter(attachmentAdapter);
        rvHistory.setAdapter(simpleNotesAdapter);

        RxView.clicks(btnHistory)
                .throttleFirst(Constants.DELAY_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> presenter.changeNotesVisibility());

        animationHelper.init(ivIconArrow, rvHistory, nsvContent_FED);
        getPresenter().subscribe();
    }

    @Override
    public void setPresenter(EmployeeDetailsContract.EmployeeDetailsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getScreenName() {
        return "Employee details screen";
    }

    @Override
    public void showHistory(boolean isShow) {
        if (isShow) {
            GoogleAnalyticHelper.trackClick(this, GoogleAnalyticHelper.EventType.CLICK_BUTTON, "History");
            animationHelper.open();
        } else {
            animationHelper.close();
        }
    }

    @Override
    public void showPersonalInformation(boolean isShown) {
        llContainerPersonalInformation_FED.setVisibility(isShown ? View.VISIBLE : View.GONE);
        tvEmptyPersonalInformation_FED.setVisibility(isShown ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showAddress(boolean isShown) {
        llAddressContainer_FED.setVisibility(isShown ? View.VISIBLE : View.GONE);
        tvEmptyAddress_FED.setVisibility(isShown ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showJobPositionList(boolean isShown) {
        llJobPositionListContainer_FED.setVisibility(isShown ? View.VISIBLE : View.GONE);
        tvEmptyJobPositionList_FED.setVisibility(isShown ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showEmploymentDetailsList(boolean isShown) {
        llEmploymentDetailsContainer_FED.setVisibility(isShown ? View.VISIBLE : View.GONE);
        tvEmptyEmploymentDetailsList_FED.setVisibility(isShown ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showAttachments(boolean isShown) {
        tvEmptyAttachments_FED.setVisibility(isShown ? View.GONE : View.VISIBLE);
    }

    @Override
    public void enableSkypeIcon(String uriPath) {
        ivSkype_FED.setVisibility(View.VISIBLE);
        RxView.clicks(ivSkype_FED)
                .throttleFirst(Constants.DELAY_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    GoogleAnalyticHelper.trackClick(this, GoogleAnalyticHelper.EventType.CLICK_SOCIAL_BUTTON, "Skype");
                    IntentActionHelper.callViewIntent(mActivity, uriPath, null);
                });
    }

    @Override
    public void enableLinkedInIcon(String uriPath) {
        ivLinkedIn_FED.setVisibility(View.VISIBLE);
        RxView.clicks(ivLinkedIn_FED)
                .throttleFirst(Constants.DELAY_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    GoogleAnalyticHelper.trackClick(this, GoogleAnalyticHelper.EventType.CLICK_SOCIAL_BUTTON, "Linked In");
                    IntentActionHelper.callViewIntent(mActivity, uriPath, null);
                });
    }

    @Override
    public void enableFacebookIcon(String uriPath) {
        ivFacebook_FED.setVisibility(View.VISIBLE);
        RxView.clicks(ivFacebook_FED)
                .throttleFirst(Constants.DELAY_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    GoogleAnalyticHelper.trackClick(this, GoogleAnalyticHelper.EventType.CLICK_SOCIAL_BUTTON, "Facebook");
                    IntentActionHelper.callViewIntent(mActivity, uriPath, null);
                });
    }

    @Override
    public void enableEmailIcon(String email) {
        ivEmail_FED.setVisibility(View.VISIBLE);
        RxView.clicks(ivEmail_FED)
                .throttleFirst(Constants.DELAY_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    GoogleAnalyticHelper.trackClick(this, GoogleAnalyticHelper.EventType.CLICK_SOCIAL_BUTTON, "Email");
                    IntentActionHelper.callSendEmailIntent(mActivity, email, null);
                });
    }

    @Override
    public void enablePersonalMobileActionClick(String phone) {
        RxView.clicks(etPersonalMobile_FED)
                .throttleFirst(Constants.DELAY_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    GoogleAnalyticHelper.trackClick(this, GoogleAnalyticHelper.EventType.CLICK_URL, "Personal Mobile");
                    IntentActionHelper.callDialIntent(mActivity, phone);
                });
    }

    @Override
    public void enableWorkPhoneActionClick(String phone) {
        RxView.clicks(etWorkPhone_FED)
                .throttleFirst(Constants.DELAY_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    GoogleAnalyticHelper.trackClick(this, GoogleAnalyticHelper.EventType.CLICK_URL, "Work Phone");
                    IntentActionHelper.callDialIntent(mActivity, phone);
                });
    }

    @Override
    public void enablePersonalEmailActionClick(String email) {
        RxView.clicks(etPersonalEmail_FED)
                .throttleFirst(Constants.DELAY_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    GoogleAnalyticHelper.trackClick(this, GoogleAnalyticHelper.EventType.CLICK_URL, "Personal Email");
                    IntentActionHelper.callSendEmailIntent(mActivity, email, null);
                });
    }

    @Override
    public void enableWorkEmailActionClick(String email) {
        RxView.clicks(etWorkEmail_FED)
                .throttleFirst(Constants.DELAY_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    GoogleAnalyticHelper.trackClick(this, GoogleAnalyticHelper.EventType.CLICK_URL, "Work Email");
                    IntentActionHelper.callSendEmailIntent(mActivity, email, null);
                });
    }

    @Override
    public void enableSourceActionClick(String url) {
        GoogleAnalyticHelper.trackClick(this, GoogleAnalyticHelper.EventType.CLICK_URL, "");
        startUrlIntent(url);
    }

    @Override
    public void setImage(String base64Image) {
        ImageHelper.getBitmapFromBase64(base64Image, new CropCircleTransformation())
                .subscribe(ivEmployeeAvatar_FED::setImageBitmap);
    }

    @Override
    public void setHeaderFullname(String fullname) {
        tvEmployeeName_FED.setText(fullname);
    }

    @Override
    public void setHeaderDepartment(String department) {
        tvHeaderDepartment_FED.setText(department);
    }

    @Override
    public void setHeaderJobPosition(String jobPosition) {
        tvHeaderJobPosition_FED.setText(jobPosition);
    }

    @Override
    public void setFirstName(String firstName) {
        etFirstName_FED.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        etLastName_FED.setText(lastName);
    }

    @Override
    public void setDateOfBirth(String dob) {
        etDob_FED.setText(dob);
    }

    @Override
    public void setPersonalMobile(String personalMobile) {
        etPersonalMobile_FED.setText(personalMobile);
    }

    @Override
    public void setPersonalEmail(String personalEmail) {
        etPersonalEmail_FED.setText(personalEmail);
    }

    @Override
    public void setSkype(String skype) {
        etSkype_FED.setText(skype);
    }

    @Override
    public void setLinkedIn(String linkedIn) {
        etLinkedIn_FED.setText(linkedIn);
    }

    @Override
    public void setFacebook(String facebook) {
        etFacebook_FED.setText(facebook);
    }

    @Override
    public void setJobPosition(String jobPosition) {
        etJobPosition_FED.setText(jobPosition);
    }

    @Override
    public void setDepartment(String department) {
        etDepartment_FED.setText(department);
    }

    @Override
    public void setManager(String manager) {
        etManager_FED.setText(manager);
    }

    @Override
    public void setJobType(String jobType) {
        etJobType_FED.setText(jobType);
    }

    @Override
    public void setSource(String source) {
        etSource_FED.setText(source);
    }

    @Override
    public void setWorkEmail(String workEmail) {
        etWorkEmail_FED.setText(workEmail);
    }

    @Override
    public void setWorkPhone(String workPhone) {
        etWorkPhone_FED.setText(workPhone);
    }

    @Override
    public void setGender(String gender) {
        etGender_FED.setText(gender);
    }

    @Override
    public void setEmploymentType(String employmentType) {
        etEmploymentType_FED.setText(employmentType);
    }

    @Override
    public void setMaritalStatus(String maritalStatus) {
        etMaritalStatus_FED.setText(maritalStatus);
    }

    @Override
    public void setNationality(String nationality) {
        etNationality_FED.setText(nationality);
    }

    @Override
    public void setIdentificationNumber(String identificationNumber) {
        eIdentificationNumber_FED.setText(identificationNumber);
    }

    @Override
    public void setPassportNumber(String passportNumber) {
        etPassportNumber_FED.setText(passportNumber);
    }

    @Override
    public void setBankAccountNumber(String bankAccountNumber) {
        etBankAccountNumber_FED.setText(bankAccountNumber);
    }

    @Override
    public void setOrderID(String orderID) {
        etOrderID_FED.setText(orderID);
    }

    @Override
    public void setStreet(String street) {
        etStreet_FPD.setText(street);
    }

    @Override
    public void setCity(String city) {
        etCity_FPD.setText(city);
    }

    @Override
    public void setState(String state) {
        etState_FPD.setText(state);
    }

    @Override
    public void setZip(String zip) {
        etZipcode_FPD.setText(zip);
    }

    @Override
    public void setCountry(String country) {
        etCountry_FPD.setText(country);
    }

    @Override
    public void setHistory(ArrayList<SimpleNoteDH> simpleNoteDHs) {
        simpleNotesAdapter.setListDH(simpleNoteDHs);
    }

    @Override
    public void displayAttachments(ArrayList<AttachmentDH> attachmentDHs) {
        attachmentAdapter.setListDH(attachmentDHs);
    }

    @Override
    public void startUrlIntent(String url) {
        IntentActionHelper.callViewIntent(mActivity, url, null);
    }

    @Override
    protected ContentPresenter getPresenter() {
        return presenter;
    }
}
