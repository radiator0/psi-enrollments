export interface IRequest {
  id?: number;
  uuid?: string;
  date?: string;
  text?: string;
  isExamined?: boolean;
  isAccepted?: boolean;
  classGroupId?: number;
  studentId?: number;
  classGroupCode?: string;
  studentName?: string;
}

export const defaultValue: Readonly<IRequest> = {
  isExamined: false,
  isAccepted: false,
};
