import { Moment } from 'moment';

export interface IEnrollment {
  id?: number;
  date?: string;
  isAdministrative?: boolean;
  studentId?: number;
  classGroupId?: number;
}

export const defaultValue: Readonly<IEnrollment> = {
  isAdministrative: false,
};
