import React, { useRef, useState } from 'react'
import { Container, Form, Button, Card, Alert } from 'react-bootstrap'
import { useAuth } from "../contexts/AuthContext"
import { Link, useHistory } from "react-router-dom"

export default function Resetpassword() {
    const emailRef = useRef();
    const { resetPassword } = useAuth();
    const [error, setError] = useState("");
    const [message, setMessage] = useState("");

    async function handleSubmit(e) {
        e.preventDefault()
        try {
            setError("")
            await resetPassword(emailRef.current.value)
            setMessage("Check Inbox to reset Password")
        } catch (error) {
            setError(error.message)
        }
    }
    return (
        <Container className="d-flex align-items-center justify-content-center" style={{ minHeight: "100vh" }}>
            <div className="w-100" style={{ maxWidth: "400px" }}>
                <Card>
                    <Card.Body>
                        <h2 className="text-center mb-4">Password Reset</h2>
                        {error && <Alert variant="danger">{error}</Alert>}
                        {message && <Alert variant="success">{message}</Alert>}
                        <Form onSubmit={handleSubmit}>
                            <Form.Group id="email">
                                <Form.Label>Email</Form.Label>
                                <Form.Control type="email" ref={emailRef} required />
                            </Form.Group>
                            <Button className="w-100" type="submit">Reset Password</Button>
                        </Form>
                    </Card.Body>
                    <div className="w-100 text-center mt-2">
                        <Link to="/" style={{ textDecoration: "none" }} >Log In</Link>
                    </div>
                </Card>
            </div></Container>
    )
}
