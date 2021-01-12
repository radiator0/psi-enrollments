import { IRequest } from 'app/shared/model/request.model';
import { IEnrollmentRight } from 'app/shared/model/enrollment-right.model';
import { IEnrollment } from 'app/shared/model/enrollment.model';

export interface IStudent {
  id?: number;
  firstName?: string;
  secondName?: string;
  lastName?: string;
  mail?: string;
  title?: string;
  requests?: IRequest[];
  enrollmentRights?: IEnrollmentRight[];
  enrollments?: IEnrollment[];
}

export const defaultValue: Readonly<IStudent> = {};
