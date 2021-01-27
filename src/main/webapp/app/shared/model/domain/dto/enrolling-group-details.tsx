import RecurringScheduleElement from './recurring-schedule-element';

export default class EnrollingGroupDetails {
    public readonly id: number;
    public readonly groupCode: string;
    public readonly enrolledCount: number;
    public readonly limit: number;
    public readonly studentEnrolled: boolean;
    public readonly canEnrollOverLimit: boolean;    
    public readonly schedules: Array<RecurringScheduleElement>;
    public readonly lecturerTitle: string;
    public readonly lecturerFirstName: string;
    public readonly lecturerSecondName: string;
    public readonly lecturerLastName: string;

    constructor(id: number, groupCode: string, enrolledCount: number, limit: number, studentEnrolled: boolean, canEnrollOverLimit: boolean,
        schedules: Array<RecurringScheduleElement>, lecturerTitle: string, lecturerFirstName: string, lecturerSecondName: string, lecturerLastName: string) {
      this.id = id;
      this.groupCode = groupCode;
      this.enrolledCount = enrolledCount;
      this.limit = limit;
      this.studentEnrolled = studentEnrolled;
      this.canEnrollOverLimit = canEnrollOverLimit;
      this.schedules = schedules;
      this.lecturerTitle = lecturerTitle;
      this.lecturerFirstName = lecturerFirstName;
      this.lecturerSecondName = lecturerSecondName;
      this.lecturerLastName = lecturerLastName;
    };
};