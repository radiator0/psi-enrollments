import React from 'react';
import { Translate, translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Col, Row } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import { handlePasswordResetInit, reset } from '../password-reset.reducer';
import { RouteComponentProps } from 'react-router-dom';
import Typography from '@material-ui/core/Typography/Typography';
import Alert from '@material-ui/lab/Alert/Alert';
import Button from '@material-ui/core/Button/Button';

export interface IPasswordResetInitProps extends DispatchProps, RouteComponentProps {}

export class PasswordResetInit extends React.Component<IPasswordResetInitProps> {
  componentWillUnmount() {
    this.props.reset();
  }

  handleValidSubmit = (event, values) => {
    this.props.handlePasswordResetInit(values.email);
    event.preventDefault();
    this.props.history.push('/');
  };

  render() {
    return (
      <div>
        <Row className="justify-content-center">
          <Col md="4">
            <Typography variant="h4" component="h4" align="center">
              <Translate contentKey="reset.request.title">Reset your password</Translate>
            </Typography>
            <Alert severity="info" style={{ marginBottom: '10px' }}>
              <Translate contentKey="reset.request.messages.info">Enter the email address you used to register</Translate>
            </Alert>
            <AvForm onValidSubmit={this.handleValidSubmit}>
              <AvField
                name="email"
                label={translate('global.form.email.label')}
                placeholder={translate('global.form.email.placeholder')}
                type="email"
                validate={{
                  required: { value: true, errorMessage: translate('global.messages.validate.email.required') },
                  minLength: { value: 5, errorMessage: translate('global.messages.validate.email.minlength') },
                  maxLength: { value: 254, errorMessage: translate('global.messages.validate.email.maxlength') },
                }}
              />
              <Button variant="contained" color="primary" type="submit" style={{ margin: '4px' }}>
                <Translate contentKey="reset.request.form.button">Reset password</Translate>
              </Button>
            </AvForm>
          </Col>
        </Row>
      </div>
    );
  }
}

const mapDispatchToProps = { handlePasswordResetInit, reset };

type DispatchProps = typeof mapDispatchToProps;

export default connect(null, mapDispatchToProps)(PasswordResetInit);
