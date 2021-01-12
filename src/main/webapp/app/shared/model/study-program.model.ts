import { IFieldOfStudy } from 'app/shared/model/field-of-study.model';
import { SemesterType } from 'app/shared/model/enumerations/semester-type.model';
import { StudyType } from 'app/shared/model/enumerations/study-type.model';
import { StudyForm } from 'app/shared/model/enumerations/study-form.model';

export interface IStudyProgram {
  id?: number;
  startYear?: number;
  endYear?: number;
  startingSemesterType?: SemesterType;
  studyType?: StudyType;
  studyForm?: StudyForm;
  fieldOfStudies?: IFieldOfStudy[];
}

export const defaultValue: Readonly<IStudyProgram> = {};
