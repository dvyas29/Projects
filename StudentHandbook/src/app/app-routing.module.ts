import { Host, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AnnouncementDetailsComponent } from './components/announcement-details/announcement-details.component';
import { AnnouncementComponent } from './components/announcement/announcement.component';
import { ContactusComponent } from './components/contactus/contactus.component';
import { FaqComponent } from './components/faq/faq.component';
import { GroupFormationComponent } from './components/group-formation/group-formation.component';
import { HomeComponent } from './components/home/home.component';
import { MainComponent } from './components/main/main.component';
import { MentorChatComponent } from './components/mentor-chat/mentor-chat.component';
import { NotesDetailsComponent } from './components/notes-details/notes-details.component';
import { NotesComponent } from './components/notes/notes.component';
import { PeerMentorshipRegistrationComponent } from './components/peer-mentorship-registration/peer-mentorship-registration.component';
import { PeerMentorshipComponent } from './components/peer-mentorship/peer-mentorship.component';
import { ProfileComponent } from './components/profile/profile.component';
import { RateApplicationComponent } from './components/rate-application/rate-application.component';
import { RateMentorComponent } from './components/rate-mentor/rate-mentor.component';
import { RequestDetailsComponent } from './components/request-details/request-details.component';
import { RequestListComponent } from './components/request-list/request-list.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { SchedulerComponent } from './components/scheduler/scheduler.component';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { SupportComponent } from './components/support/support.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { AddmemberComponent } from './components/add-member/addmember.component';
import { ModifymemberComponent } from './components/modify-member/modifymember.component';
import { GroupManagementComponent } from './components/group-management/group-management.component';
import { ManageRegistrationComponent } from './components/manage-registration/manage-registration.component';
import { StudentWallComponent } from './components/student-wall/student-wall.component';

const routes: Routes = [
  { path: '', redirectTo: 'signin', pathMatch: 'full' },
  { path: 'signin', component: SignInComponent },
  { path: 'signup', component: SignUpComponent },
  { path: 'forgotPassword', component: ForgotPasswordComponent},
  { path: 'main', component: MainComponent, children:[
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'home', component: HomeComponent },
    { path: 'announcement', component: AnnouncementComponent },
    { path: 'announcementdetails', component: AnnouncementDetailsComponent },
    { path: 'scheduler', component: SchedulerComponent },
    { path: 'notes', component: NotesComponent },
    { path: 'notes-detail', component: NotesDetailsComponent },
    { path: 'support', component: SupportComponent },
    { path: 'faq', component: FaqComponent },
    { path: 'contactus', component: ContactusComponent },
    { path: 'rate-mentor', component: RateMentorComponent },
    { path: 'rate-application', component: RateApplicationComponent},
    { path: 'profile', component: ProfileComponent },
    { path: 'resetpassword', component: ResetPasswordComponent },
    { path: 'peermentorship', component: PeerMentorshipComponent },
    { path: 'peermentorshipregistration', component: PeerMentorshipRegistrationComponent },
    { path: 'requestlist', component: RequestListComponent },
    { path: 'requestdetails', component: RequestDetailsComponent },
    { path: 'group-management', component: GroupManagementComponent },
    { path: 'mentorchat', component: MentorChatComponent },
    { path: 'addmember', component: AddmemberComponent },
    { path: 'modifymember', component: ModifymemberComponent },
    { path: 'manageregistration', component: ManageRegistrationComponent},
    { path: 'studentwall', component: StudentWallComponent},
    { path: 'scheduler', component: SchedulerComponent}
  ] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
