import './login-modal.scss';
import React from 'react';
import { Translate, translate } from 'react-jhipster';
import { Modal, ModalHeader, ModalBody, ModalFooter, Label, Row, Col } from 'reactstrap';
import { AvForm, AvField, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Link } from 'react-router-dom';
import TextField from '@material-ui/core/TextField/TextField';
import Button from '@material-ui/core/Button';
import Alert from '@material-ui/lab/Alert';

export interface ILoginModalProps {
  showModal: boolean;
  loginError: boolean;
  handleLogin: Function;
  handleClose;
}

class LoginModal extends React.Component<ILoginModalProps> {
  handleSubmit = (event, errors, { username, password, rememberMe }) => {
    const { handleLogin } = this.props;
    handleLogin(username, password, rememberMe);
  };

  render() {
    const { loginError, handleClose } = this.props;

    return (
      <Modal centered isOpen={this.props.showModal} toggle={handleClose} backdrop="static" id="login-page" autoFocus={false}>
        <AvForm onSubmit={this.handleSubmit}>
          <ModalHeader id="login-title" toggle={handleClose}>
            <Translate contentKey="login.title">Sign in</Translate>
          </ModalHeader>
          <ModalBody>
            <Row>
              <Col md="12">
                {loginError ? (
                  <Alert severity="error" style={{ marginBottom: '8px' }}>
                    <Translate contentKey="login.messages.error.authentication">
                      <strong>Failed to sign in!</strong> Please check your credentials and try again.
                    </Translate>
                  </Alert>
                ) : null}
              </Col>
              <Col md="12">
                <AvField
                  name="username"
                  label={translate('global.form.username.label')}
                  placeholder={translate('global.form.username.placeholder')}
                  required
                  errorMessage={translate('global.form.username.label') + translate('login.cannot-empty')}
                  autoFocus
                />
                <AvField
                  name="password"
                  type="password"
                  label={translate('login.form.password')}
                  placeholder={translate('login.form.password.placeholder')}
                  required
                  errorMessage={translate('login.form.password') + translate('login.cannot-empty')}
                />
              </Col>
            </Row>
            <Alert severity="info">
              <Link to="/account/reset/request">
                <Translate contentKey="login.password.forgot">Did you forget your password?</Translate>
              </Link>
            </Alert>
          </ModalBody>
          <ModalFooter>
            <Button onClick={handleClose} variant="contained" color="secondary" style={{ margin: '4px' }}>
              Cancel
            </Button>
            <Button variant="contained" color="primary" type="submit" style={{ margin: '4px' }}>
              Sign In
            </Button>
          </ModalFooter>
        </AvForm>
      </Modal>
    );
  }
}

export default LoginModal;
