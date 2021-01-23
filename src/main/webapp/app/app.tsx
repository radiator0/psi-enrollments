import 'react-toastify/dist/ReactToastify.css';
import './app.scss';

import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Card } from 'reactstrap';
import { BrowserRouter as Router } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import { hot } from 'react-hot-loader';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { getProfile } from 'app/shared/reducers/application-profile';
import { setLocale } from 'app/shared/reducers/locale';
import Footer from 'app/shared/layout/footer/footer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import ErrorBoundary from 'app/shared/error/error-boundary';
import { AUTHORITIES } from 'app/config/constants';
import AppRoutes from 'app/routes';
import Nav from './shared/layout/nav/nav';
import { makeStyles, Theme, createStyles } from '@material-ui/core';
import useTheme from '@material-ui/core/styles/useTheme';
import CssBaseline from '@material-ui/core/CssBaseline/CssBaseline';
import LoadingBar from 'react-redux-loading-bar';

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');
const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: 'flex',
    },
    content: {
      flexGrow: 1,
      padding: theme.spacing(3),
    },
  })
);

export interface IAppProps extends StateProps, DispatchProps {}

export const App = (props: IAppProps) => {
  useEffect(() => {
    props.getSession();
    props.getProfile();
  }, []);

  const paddingTop = '60px';
  const classes = useStyles();

  const theme = useTheme();

  return (
    <Router basename={baseHref}>
      <div className="app-container" style={{ paddingTop }}>
        <ToastContainer position={toast.POSITION.TOP_LEFT} className="toastify-container" toastClassName="toastify-toast" />
        <div className={classes.root}>
          <CssBaseline />
          <ErrorBoundary>
            <Nav
              isAuthenticated={props.isAuthenticated}
              onLocaleChange={props.setLocale}
              currentLocale={props.currentLocale}
              isStudent={props.isStudent}
              isLecturer={props.isLecturer}
            />
          </ErrorBoundary>

          {/* <ErrorBoundary>
          <Header
            isAuthenticated={props.isAuthenticated}
            isStudent={props.isStudent}
            isLecturer={props.isLecturer}
            isAdmin={props.isAdmin}
            currentLocale={props.currentLocale}
            onLocaleChange={props.setLocale}
            ribbonEnv={props.ribbonEnv}
            isInProduction={props.isInProduction}
            isSwaggerEnabled={props.isSwaggerEnabled}
          />
        </ErrorBoundary> */}
          <LoadingBar className="loading-bar" />
          <div className="container-fluid view-container" id="app-view-container">
            <Card className="jh-card">
              <ErrorBoundary>
                <AppRoutes />
              </ErrorBoundary>
            </Card>
            <Footer />
          </div>
        </div>
      </div>
    </Router>
  );
};

const mapStateToProps = ({ authentication, applicationProfile, locale }: IRootState) => ({
  currentLocale: locale.currentLocale,
  isAuthenticated: authentication.isAuthenticated,
  isAdmin: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN]),
  isStudent: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.STUDENT]),
  isLecturer: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.LECTURER]),
  ribbonEnv: applicationProfile.ribbonEnv,
  isInProduction: applicationProfile.inProduction,
  isSwaggerEnabled: applicationProfile.isSwaggerEnabled,
});

const mapDispatchToProps = { setLocale, getSession, getProfile };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(hot(module)(App));
