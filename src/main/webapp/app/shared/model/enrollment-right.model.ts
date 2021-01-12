import { Moment } from 'moment';

export interface IEnrollmentRight {
  id?: number;
  startDate?: string;
  enrollmentDateId?: number;
  specialtyId?: number;
  studentId?: number;
}

export const defaultValue: Readonly<IEnrollmentRight> = {};
