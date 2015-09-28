#
# Keep apt db uncorrupt: No timeout.
#

class { 'apt': update_timeout => 60 }

exec { 'apt-update':
  command => 'apt-get update',
  path    => '/bin:/usr/bin',
  timeout => 0
}

apt::ppa { 'ppa:wnoronha/thrift':
  before => Exec['apt-update']
}

package { [
  'gradle',
  'openjdk-7-jdk',
  'thrift-compiler',
  'tree'
  ]:
  ensure  => present,
  require => Exec['apt-update']
}

file { '/home/vagrant/.bash_profile':
  ensure => link,
  target => '/vagrant/.bash_profile',
  owner  => 'vagrant',
  group  => 'vagrant'
}
