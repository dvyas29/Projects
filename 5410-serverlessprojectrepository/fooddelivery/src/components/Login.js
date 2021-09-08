import React, { useRef, useState } from 'react'
import { Container, Form, Button, Card, Alert } from 'react-bootstrap'
import { useAuth } from "../contexts/AuthContext"
import { Link, useHistory } from "react-router-dom"

export default function Login() {
    const emailRef = useRef();
    const passwordRef = useRef();
    const { login } = useAuth();
    const [error, setError] = useState("");
    const history = useHistory();

    async function handleSubmit(e) {
        e.preventDefault()
        try {
            setError("")
            await login(emailRef.current.value, passwordRef.current.value).then(response => {
                console.log(response.user)
                const data = {
                    email: emailRef.current.value,
                    token: response.user.refreshToken
                }
                history.push('/security-page', data);
            })
        } catch (error) {
            setError(error.message)
        }
    }
    return (
        <Container className="d-flex align-items-center justify-content-center" style={{ minHeight: "100vh" }}>
            <div className="w-100" style={{ maxWidth: "400px" }}>

                <Card>
                    <Card.Body>
                        <h2 className="text-center mb-4">Log In</h2>
                        {error && <Alert variant="danger">{error}</Alert>}
                        <Form onSubmit={handleSubmit}>
                            <Form.Group id="email">
                                <Form.Label>Email</Form.Label>
                                <Form.Control type="email" ref={emailRef} required />
                            </Form.Group>
                            <Form.Group id="password">
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" ref={passwordRef} required />
                            </Form.Group>
                            <Button className="w-100" type="submit">Log In</Button>
                            <div className="w-100 text-center mt-3">
                                <Link to="/reset-password" style={{ textDecoration: "none" }} >Forget Password?</Link>
                            </div>
                        </Form>
                    </Card.Body>
                    <div className="w-100 text-center mt-2">
                        Need an account? <Link to="/signup" style={{ textDecoration: "none" }} >Sign Up</Link>
                    </div>
                </Card>
            </div>
        </Container>
    )
}
