import { Moment } from 'moment';
import { IEnrollmentDate } from 'app/shared/model/enrollment-date.model';
import { SemesterType } from 'app/shared/model/enumerations/semester-type.model';

export interface ISemester {
  id?: number;
  number?: number;
  semesterType?: SemesterType;
  startDate?: string;
  enrollmentDates?: IEnrollmentDate[];
  fieldOfStudyId?: number;
}

export const defaultValue: Readonly<ISemester> = {};
