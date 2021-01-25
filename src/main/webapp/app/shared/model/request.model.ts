import { Moment } from 'moment';

export interface IRequest {
  id?: number;
  uuid?: string;
  date?: string;
  text?: string;
  isExamined?: boolean;
  isAccepted?: boolean;
  classGroupId?: number;
  studentId?: number;
}

export const defaultValue: Readonly<IRequest> = {
  isExamined: false,
};
