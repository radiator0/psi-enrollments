import { Moment } from 'moment';
import { IEnrollmentUnit } from 'app/shared/model/enrollment-unit.model';
import { IEnrollmentRight } from 'app/shared/model/enrollment-right.model';
import { ICourse } from 'app/shared/model/course.model';

export interface IEnrollmentDate {
  id?: number;
  name?: string;
  isPreEnrollment?: boolean;
  startDate?: string;
  endDate?: string;
  enrollmentUnits?: IEnrollmentUnit[];
  enrollmentRights?: IEnrollmentRight[];
  courses?: ICourse[];
  semesterId?: number;
}

export const defaultValue: Readonly<IEnrollmentDate> = {
  isPreEnrollment: false,
};
