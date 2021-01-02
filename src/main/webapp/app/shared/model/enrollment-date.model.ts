import { Moment } from 'moment';
import { IEnrollmentUnit } from 'app/shared/model/enrollment-unit.model';
import { IEnrollmentRight } from 'app/shared/model/enrollment-right.model';

export interface IEnrollmentDate {
  id?: number;
  name?: string;
  isPreEnrollment?: boolean;
  startDate?: Moment;
  endDate?: Moment;
  enrollmentUnits?: IEnrollmentUnit[];
  enrollmentRights?: IEnrollmentRight[];
  semesterId?: number;
  courseId?: number;
}

export const defaultValue: Readonly<IEnrollmentDate> = {
  isPreEnrollment: false
};
