import { IEnrollmentRight } from 'app/shared/model/enrollment-right.model';
import { ICourse } from 'app/shared/model/course.model';

export interface ISpecialty {
  id?: number;
  name?: string;
  enrollmentRights?: IEnrollmentRight[];
  courses?: ICourse[];
}

export const defaultValue: Readonly<ISpecialty> = {};
