/**
* Author: Gurleen Saluja (gr997570@dal.ca)
* Model for Peer Mentorship Registered Users.
*/
export class PeerMentorshipRegistrationModel {
  _id: number = -1;
  email: string;
  name: string;
  role: string;
  faculty: string;
  program: string;
  startDate: any;
  endDate: any;
  location: string;
  preference: string;
  campusLocation: string;
  requestType: string;
  requestDate: any;
  requestStatus: string;
  modificationDate: any;
  mode: string;
  isRegistered: boolean;
}
