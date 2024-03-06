import React from 'react';
import { GoogleLogin } from 'react-google-login';
import { GOOGLE_USER_CLIENT_ID } from './constants';

const responseGoogle = (response) => {
  console.log(response);
};

const GoogleAuth = () => {
  return (
    <div>
      <GoogleLogin
        clientId={GOOGLE_USER_CLIENT_ID}
        buttonText="Login with Google"
        onSuccess={responseGoogle}
        onFailure={responseGoogle}
        cookiePolicy={'single_host_origin'}
      />
    </div>
  );
};

export default GoogleAuth;
