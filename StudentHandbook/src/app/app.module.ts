import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './components/header/header.component';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { AnnouncementComponent } from './components/announcement/announcement.component';
import { AnnouncementDetailsComponent } from './components/announcement-details/announcement-details.component';
import { StudentWallComponent } from './components/student-wall/student-wall.component';
import { StudentWallDetailsComponent } from './components/student-wall-details/student-wall-details.component';
import { SchedulerComponent } from './components/scheduler/scheduler.component';
import { SchedulerDetailsComponent } from './components/scheduler-details/scheduler-details.component';
import { NotesComponent } from './components/notes/notes.component';
import { NotesDetailsComponent } from './components/notes-details/notes-details.component';
import { SupportComponent } from './components/support/support.component';
import { FaqComponent } from './components/faq/faq.component';
import { ContactusComponent } from './components/contactus/contactus.component';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { ProfileComponent } from './components/profile/profile.component';
import { PeerMentorshipComponent } from './components/peer-mentorship/peer-mentorship.component';
import { PeerMentorshipRegistrationComponent } from './components/peer-mentorship-registration/peer-mentorship-registration.component';
import { RequestListComponent } from './components/request-list/request-list.component';
import { RequestDetailsComponent } from './components/request-details/request-details.component';
import { GroupFormationComponent } from './components/group-formation/group-formation.component';
import { MentorChatComponent } from './components/mentor-chat/mentor-chat.component';
import { MainComponent } from './components/main/main.component';
import { MessageComponent } from './components/message/message.component';

import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatChipsModule } from '@angular/material/chips';
import { MatRippleModule } from '@angular/material/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialogModule } from '@angular/material/dialog';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatListModule } from '@angular/material/list';
import { MatExpansionModule } from '@angular/material/expansion';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatTabsModule} from '@angular/material/tabs';
import { RateMentorComponent } from './components/rate-mentor/rate-mentor.component';
import {MatSelectModule} from '@angular/material/select';
import { RateApplicationComponent } from './components/rate-application/rate-application.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { RequesterDetailsModalComponent } from './components/request-list/requester-details-modal/requester-details-modal.component';
import { AddmemberComponent } from './components/add-member/addmember.component';
import { ModifymemberComponent } from './components/modify-member/modifymember.component';
import { GroupManagementComponent } from './components/group-management/group-management.component';
import { AssignMentorComponent } from './components/assign-mentor/assign-mentor.component';

import { FormsModule, ReactiveFormsModule, } from '@angular/forms'
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatStepperModule } from '@angular/material/stepper';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { MatRadioModule } from '@angular/material/radio';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { HttpClientModule } from '@angular/common/http';
import { CreateGroupComponent } from './components/create-group/create-group.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { ManageRegistrationComponent } from './components/manage-registration/manage-registration.component';
import { MatNativeDateModule } from '@angular/material/core';
import { MatMomentDateModule } from "@angular/material-moment-adapter";
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    SidenavComponent,
    AnnouncementComponent,
    AnnouncementDetailsComponent,
    StudentWallComponent,
    StudentWallDetailsComponent,
    SchedulerComponent,
    SchedulerDetailsComponent,
    NotesComponent,
    NotesDetailsComponent,
    SupportComponent,
    FaqComponent,
    ContactusComponent,
    SignInComponent,
    SignUpComponent,
    ResetPasswordComponent,
    ProfileComponent,
    PeerMentorshipComponent,
    PeerMentorshipRegistrationComponent,
    RequestListComponent,
    RequestDetailsComponent,
    GroupFormationComponent,
    MentorChatComponent,
    MainComponent,
    MessageComponent,
    RateMentorComponent,
    RateApplicationComponent,
    RequesterDetailsModalComponent,
    ForgotPasswordComponent,
    AddmemberComponent,
    ModifymemberComponent,
    GroupManagementComponent,
    AssignMentorComponent,
    CreateGroupComponent,
    ManageRegistrationComponent
  ],
  imports: [
    BrowserModule,
    MatProgressSpinnerModule,
    AppRoutingModule,
    MatFormFieldModule,
    MatCardModule,
    MatStepperModule,
    MatInputModule,
    MatButtonModule,
    FormsModule, ReactiveFormsModule,
    BrowserAnimationsModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatIconModule,
    MatMenuModule,
    MatChipsModule,
    MatCardModule,
    MatRippleModule,
    MatTooltipModule,
    MatDialogModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatAutocompleteModule,
    MatStepperModule,
    MatListModule,
    MatExpansionModule,
    MatSnackBarModule,
    MatTabsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatRadioModule,
    MatDatepickerModule,
    HttpClientModule,
    MatCheckboxModule,
    MatNativeDateModule,
    MatMomentDateModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
