import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StudyProgram from './study-program';
import FieldOfStudy from './field-of-study';
import Semester from './semester';
import EnrollmentUnit from './enrollment-unit';
import EnrollmentDate from './enrollment-date';
import EnrollmentRight from './enrollment-right';
import Student from './student';
import Request from './request';
import Specialty from './specialty';
import Course from './course';
import ClassGroup from './class-group';
import Enrollment from './enrollment';
import Lecturer from './lecturer';
import CourseUnit from './course-unit';
import SelectableModule from './selectable-module';
import ClassSchedule from './class-schedule';
import Building from './building';
import Room from './room';
import ClassUnit from './class-unit';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/study-program`} component={StudyProgram} />
      <ErrorBoundaryRoute path={`${match.url}/field-of-study`} component={FieldOfStudy} />
      <ErrorBoundaryRoute path={`${match.url}/semester`} component={Semester} />
      <ErrorBoundaryRoute path={`${match.url}/enrollment-unit`} component={EnrollmentUnit} />
      <ErrorBoundaryRoute path={`${match.url}/enrollment-date`} component={EnrollmentDate} />
      <ErrorBoundaryRoute path={`${match.url}/enrollment-right`} component={EnrollmentRight} />
      <ErrorBoundaryRoute path={`${match.url}/student`} component={Student} />
      <ErrorBoundaryRoute path={`${match.url}/request`} component={Request} />
      <ErrorBoundaryRoute path={`${match.url}/specialty`} component={Specialty} />
      <ErrorBoundaryRoute path={`${match.url}/course`} component={Course} />
      <ErrorBoundaryRoute path={`${match.url}/class-group`} component={ClassGroup} />
      <ErrorBoundaryRoute path={`${match.url}/enrollment`} component={Enrollment} />
      <ErrorBoundaryRoute path={`${match.url}/lecturer`} component={Lecturer} />
      <ErrorBoundaryRoute path={`${match.url}/course-unit`} component={CourseUnit} />
      <ErrorBoundaryRoute path={`${match.url}/selectable-module`} component={SelectableModule} />
      <ErrorBoundaryRoute path={`${match.url}/class-schedule`} component={ClassSchedule} />
      <ErrorBoundaryRoute path={`${match.url}/building`} component={Building} />
      <ErrorBoundaryRoute path={`${match.url}/room`} component={Room} />
      <ErrorBoundaryRoute path={`${match.url}/class-unit`} component={ClassUnit} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
