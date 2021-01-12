import { Moment } from 'moment';

export interface IEnrollmentUnit {
  id?: number;
  startDate?: string;
  endDate?: string;
  enrollmentDateId?: number;
}

export const defaultValue: Readonly<IEnrollmentUnit> = {};
