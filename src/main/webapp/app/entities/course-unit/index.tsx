import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseUnit from './course-unit';
import CourseUnitDetail from './course-unit-detail';
import CourseUnitUpdate from './course-unit-update';
import CourseUnitDeleteDialog from './course-unit-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseUnitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseUnitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseUnitDetail} />
      <ErrorBoundaryRoute path={match.url} component={CourseUnit} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseUnitDeleteDialog} />
  </>
);

export default Routes;
