#!/usr/bin/perl

use strict;
use warnings;

use Email::Stuffer;
use Email::Sender::Transport::SMTP;
use Try::Tiny;

print "Content-Type: text/html\n\n";
try {
# SMTP Transport and Authentication
my %smtp = (
    host => 'smtp.office365.com',
    port => 587,
    ssl => 'STARTTLS',
    sasl_username => 'darkperlcgi2@outlook.com',
    sasl_password => '!igclrepkrad_'
);

my $transport = Email::Sender::Transport::SMTP->new(%smtp);


# Create and send the email in one shot
my $email = Email::Stuffer->from     ('darkperlcgi2@outlook.com')
                ->to       ('jeffersondiassantos@gmail.com')
                ->subject  ('Test subject')
                ->text_body("Test email from perl in docker")
                ->transport($transport);

eval {
    $email->send_or_die;
    1;
} or do {
    print "sendMail(): Error sending mail: $@";
};
} catch {
  my $error = $_;
  print "Sending failed: $error->message";
};

print "Hello cgi world";
