import React from 'react';
import { Switch } from 'react-router-dom';
import Loadable from 'react-loadable';

import Login from 'app/modules/login/login';
import Activate from 'app/modules/account/activate/activate';
import PasswordResetInit from 'app/modules/account/password-reset/init/password-reset-init';
import PasswordResetFinish from 'app/modules/account/password-reset/finish/password-reset-finish';
import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';
import Entities from 'app/entities';
import Schedule from 'app/modules/schedule/schedule';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PageNotFound from 'app/shared/error/page-not-found';
import { AUTHORITIES } from 'app/config/constants';
import AsksForEnrollmentOverLimit from './modules/asks-for-enrollment-over-limit/asks-for-enrollment-over-limit';
import Enrollments from './modules/enrollments/enrollments';
import Enrolling from './modules/enrolling/enrolling';
import GroupDetails from './modules/enrolling/group-details/group-details';
import DocsPage from './modules/administration/docs/docs';

const Account = Loadable({
  loader: () => import(/* webpackChunkName: "account" */ 'app/modules/account'),
  loading: () => <div>loading ...</div>,
});

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => <div>loading ...</div>,
});

const Routes = () => (
  <div className="view-routes">
    <Switch>
      <ErrorBoundaryRoute path="/login" component={Login} />
      <ErrorBoundaryRoute path="/logout" component={Logout} />
      {/* <ErrorBoundaryRoute path="/account/register" component={Register} /> */}
      <ErrorBoundaryRoute path="/account/activate/:key?" component={Activate} />
      <ErrorBoundaryRoute path="/account/reset/request" component={PasswordResetInit} />
      <ErrorBoundaryRoute path="/account/reset/finish/:key?" component={PasswordResetFinish} />
      <PrivateRoute path="/schedule/:scheduleType" component={Schedule} hasAnyAuthorities={[AUTHORITIES.STUDENT, AUTHORITIES.LECTURER]} />
      <PrivateRoute path="/docs" component={DocsPage} />
      <PrivateRoute path="/admin" component={Admin} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
      <PrivateRoute
        path="/account"
        component={Account}
        hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.STUDENT, AUTHORITIES.LECTURER]}
      />
      <PrivateRoute
        path="/asks-for-enrollment-over-limit"
        component={AsksForEnrollmentOverLimit}
        hasAnyAuthorities={[AUTHORITIES.STUDENT, AUTHORITIES.LECTURER]}
      />
      <PrivateRoute path="/enrollments" component={Enrollments} hasAnyAuthorities={[AUTHORITIES.STUDENT]} />
      <PrivateRoute path="/enrolling" component={Enrolling} hasAnyAuthorities={[AUTHORITIES.STUDENT]} />
      <PrivateRoute path="/group-details/:groupCode" component={GroupDetails} hasAnyAuthorities={[AUTHORITIES.STUDENT, AUTHORITIES.LECTURER]} />
      <ErrorBoundaryRoute path="/" exact component={Home} />
      <PrivateRoute path="/" component={Entities} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
      <ErrorBoundaryRoute component={PageNotFound} />
    </Switch>
  </div>
);

export default Routes;
