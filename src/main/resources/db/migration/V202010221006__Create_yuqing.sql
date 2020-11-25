CREATE TABLE IF NOT EXISTS `yuqing` (
  `id` varchar(255) DEFAULT NULL,
  `title` text,
  `release_date` varchar(255) DEFAULT NULL,
  `url` text,
  `media_name` varchar(255) DEFAULT NULL,
  `add_time` varchar(255) DEFAULT NULL,
  `relativity` varchar(255) DEFAULT NULL,
  `feature_words` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `influence` varchar(255) DEFAULT NULL,
  `emotional` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `content` longtext,
  `cd_time` datetime DEFAULT NULL,
  `itime` datetime DEFAULT NULL,
  `fromtime` varchar(255) DEFAULT NULL,
  `endtime` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;