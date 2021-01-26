import './login-modal.scss';
import React from 'react';
import { Translate, translate } from 'react-jhipster';
import Typography from '@material-ui/core/Typography/Typography';
import TextField from '@material-ui/core/TextField/TextField';
import Grid from '@material-ui/core/Grid/Grid';
import { Button, FormControlLabel, Switch } from '@material-ui/core';

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
      <Grid container spacing={3}>
        <Grid item xs={12}>
          <Typography variant="h4" component="h4" align="center">
            Sign in
          </Typography>
        </Grid>
        <Grid item xs={4}></Grid>
        <Grid item xs={4} alignContent="center">
          <Grid container>
            <form noValidate autoComplete="off">
              <Grid item xs={12}>
                <TextField style={{ margin: 8 }} fullWidth required id="outlined-required" label="Username" variant="outlined" />
                <TextField style={{ margin: 8 }} fullWidth required id="outlined-required" label="Password" variant="outlined" />
              </Grid>
              <Grid item xs={12}>
                <FormControlLabel style={{ margin: 8 }} control={<Switch />} label="Remember me" />
              </Grid>
              <Grid item xs={12} alignContent="flex-end">
                <Button variant="contained" color="secondary">
                  Cancel
                </Button>
                <Button variant="contained" color="primary">
                  Sign In
                </Button>
              </Grid>
            </form>
          </Grid>
        </Grid>
        <Grid item xs={4}></Grid>
      </Grid>
    );
  }
}

export default LoginModal;
