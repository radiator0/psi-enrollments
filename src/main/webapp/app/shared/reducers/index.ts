import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import studyProgram, {
  StudyProgramState
} from 'app/entities/study-program/study-program.reducer';
// prettier-ignore
import fieldOfStudy, {
  FieldOfStudyState
} from 'app/entities/field-of-study/field-of-study.reducer';
// prettier-ignore
import semester, {
  SemesterState
} from 'app/entities/semester/semester.reducer';
// prettier-ignore
import enrollmentUnit, {
  EnrollmentUnitState
} from 'app/entities/enrollment-unit/enrollment-unit.reducer';
// prettier-ignore
import enrollmentDate, {
  EnrollmentDateState
} from 'app/entities/enrollment-date/enrollment-date.reducer';
// prettier-ignore
import enrollmentRight, {
  EnrollmentRightState
} from 'app/entities/enrollment-right/enrollment-right.reducer';
// prettier-ignore
import student, {
  StudentState
} from 'app/entities/student/student.reducer';
// prettier-ignore
import request, {
  RequestState
} from 'app/entities/request/request.reducer';
// prettier-ignore
import specialty, {
  SpecialtyState
} from 'app/entities/specialty/specialty.reducer';
// prettier-ignore
import course, {
  CourseState
} from 'app/entities/course/course.reducer';
// prettier-ignore
import classGroup, {
  ClassGroupState
} from 'app/entities/class-group/class-group.reducer';
// prettier-ignore
import enrollment, {
  EnrollmentState
} from 'app/entities/enrollment/enrollment.reducer';
// prettier-ignore
import lecturer, {
  LecturerState
} from 'app/entities/lecturer/lecturer.reducer';
// prettier-ignore
import courseUnit, {
  CourseUnitState
} from 'app/entities/course-unit/course-unit.reducer';
// prettier-ignore
import selectableModule, {
  SelectableModuleState
} from 'app/entities/selectable-module/selectable-module.reducer';
// prettier-ignore
import classSchedule, {
  ClassScheduleState
} from 'app/entities/class-schedule/class-schedule.reducer';
// prettier-ignore
import building, {
  BuildingState
} from 'app/entities/building/building.reducer';
// prettier-ignore
import room, {
  RoomState
} from 'app/entities/room/room.reducer';
// prettier-ignore
import classUnit, {
  ClassUnitState
} from 'app/entities/class-unit/class-unit.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly studyProgram: StudyProgramState;
  readonly fieldOfStudy: FieldOfStudyState;
  readonly semester: SemesterState;
  readonly enrollmentUnit: EnrollmentUnitState;
  readonly enrollmentDate: EnrollmentDateState;
  readonly enrollmentRight: EnrollmentRightState;
  readonly student: StudentState;
  readonly request: RequestState;
  readonly specialty: SpecialtyState;
  readonly course: CourseState;
  readonly classGroup: ClassGroupState;
  readonly enrollment: EnrollmentState;
  readonly lecturer: LecturerState;
  readonly courseUnit: CourseUnitState;
  readonly selectableModule: SelectableModuleState;
  readonly classSchedule: ClassScheduleState;
  readonly building: BuildingState;
  readonly room: RoomState;
  readonly classUnit: ClassUnitState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  studyProgram,
  fieldOfStudy,
  semester,
  enrollmentUnit,
  enrollmentDate,
  enrollmentRight,
  student,
  request,
  specialty,
  course,
  classGroup,
  enrollment,
  lecturer,
  courseUnit,
  selectableModule,
  classSchedule,
  building,
  room,
  classUnit,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
