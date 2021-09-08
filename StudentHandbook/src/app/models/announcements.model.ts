/* 
 * Author: Mansi Singh 
 * Email id: mn518448@dal.ca
*/

//model class for announcement
export class Announcement{
    _id:number=-1;
    title:string="";
    category:string="";
    description: string="";
    createdByName: string="";
    createdById: string="";
    createdOn: Date;
    isReadMore: boolean=false;
}