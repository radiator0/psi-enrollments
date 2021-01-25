import RequestOverLimit from '../../shared/model/domain/dto/request-over-limit';
import RecurringScheduleElement from '../../shared/model/domain/dto/recurring-schedule-element';

export default class GroupsData {
    public readonly id: number;
    public readonly groupCode: string;
    public readonly enrolledCount: number;
    public readonly limit: number;
    public readonly isLimitReached: boolean;
    public readonly isStudentEnrolled: boolean;
    public readonly canEnrollOverLimit: boolean;
    public readonly requestOverLimit: RequestOverLimit;
    public readonly schedules: Array<RecurringScheduleElement>;
    public readonly lecturerTitle: string;
    public readonly lecturerFirstName: string;
    public readonly lecturerSecondName: string;
    public readonly lecturerLastName: string;

    constructor(id: number, groupCode: string, enrolledCount: number, limit: number, isStudentEnrolled: boolean, canEnrollOverLimit: boolean, requestOverLimit: RequestOverLimit,
        schedules: Array<RecurringScheduleElement>, lecturerTitle: string, lecturerFirstName: string, lecturerSecondName: string, lecturerLastName: string) {
      this.id = id;
      this.groupCode = groupCode;
      this.enrolledCount = enrolledCount;
      this.limit = limit;
      this.isLimitReached = enrolledCount >= limit;
      this.isStudentEnrolled = isStudentEnrolled;
      this.canEnrollOverLimit = canEnrollOverLimit;
      this.requestOverLimit = requestOverLimit;
      this.schedules = schedules;
      this.lecturerTitle = lecturerTitle;
      this.lecturerFirstName = lecturerFirstName;
      this.lecturerSecondName = lecturerSecondName;
      this.lecturerLastName = lecturerLastName;
    };
}