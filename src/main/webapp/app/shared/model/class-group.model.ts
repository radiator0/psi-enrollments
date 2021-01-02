import { IRequest } from 'app/shared/model/request.model';
import { IEnrollment } from 'app/shared/model/enrollment.model';
import { IClassUnit } from 'app/shared/model/class-unit.model';
import { IClassSchedule } from 'app/shared/model/class-schedule.model';

export interface IClassGroup {
  id?: number;
  code?: string;
  isEnrollmentAboveLimitAllowed?: boolean;
  peopleLimit?: number;
  enrolledCount?: number;
  isFull?: boolean;
  requests?: IRequest[];
  enrollments?: IEnrollment[];
  classUnits?: IClassUnit[];
  classSchedules?: IClassSchedule[];
  courseId?: number;
  lecturerId?: number;
}

export const defaultValue: Readonly<IClassGroup> = {
  isEnrollmentAboveLimitAllowed: false,
  isFull: false
};
