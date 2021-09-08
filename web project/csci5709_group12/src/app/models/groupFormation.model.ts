export class GroupFormation {
    public _id: number = -1;
    public groupName: string = "";
    public location: string= "";
    public faculty: string = "";
    public mentee : any[] = [];
}

export class Mentee {
    public _id: number = -1;
    public name: string = "";
    public program: string= "";
    public checked: boolean ;
    public campusLocation: string
    public email: string
    public endDate: string
    public faculty: string
    public group: any
    public location: string
    public modificationDate: string
    public preference: string
    public requestDate: string
    public requestStatus: string
    public requestType: string
    public role: string
    public startDate: string
}